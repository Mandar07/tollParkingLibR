package com.modeparking.model.impl;

import com.modeparking.config.PolicyConfig;
import com.modeparking.data.ParkingBill;
import com.modeparking.data.ParkingPolicy;
import com.modeparking.data.ParkingSlot;
import com.modeparking.data.ParkingSlotType;
import com.modeparking.data.Repository.ParkingBillRepository;
import com.modeparking.data.Repository.ParkingSlotRepository;
import com.modeparking.model.ITollParkingAgent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.IntStream;

@Component
public class TollParkingAgent implements ITollParkingAgent {

    private boolean hasIntitialzed;
    private ParkingBillRepository parkingBillRepository;
    private ParkingSlotRepository parkingSlotRepository;
    private PricingService pricingService;
    private PolicyConfig policyConfig;

    @Autowired
    public TollParkingAgent(ParkingBillRepository parkingBillRepository, ParkingSlotRepository parkingSlotRepository,
                            PricingService pricingService){
        this.parkingBillRepository = parkingBillRepository;
        this.parkingSlotRepository = parkingSlotRepository;
        this.pricingService = pricingService;
    }

    public PolicyConfig initialize(PolicyConfig policyConfig){
        if (!this.hasIntitialzed){
            int numStdParkingSlot = policyConfig.getNumStdParkingSlot();
            int numEltCar20kwParkingSlot = policyConfig.getNumEltCar20kwParkingSlot();
            int numEltCar50kwParkingSlot = policyConfig.getNumEltCar50kwParkingSlot();
            IntStream.rangeClosed(1, numStdParkingSlot)
                    .forEach(
                            i -> parkingSlotRepository.save(new ParkingSlot(ParkingSlotType.STANDARD, true)));
            IntStream.rangeClosed(1, numEltCar20kwParkingSlot)
                    .forEach(
                            i -> parkingSlotRepository.save(new ParkingSlot(ParkingSlotType.EltCar20kw, true)));
            IntStream.rangeClosed(1, numEltCar50kwParkingSlot)
                    .forEach(
                            i -> parkingSlotRepository.save(new ParkingSlot(ParkingSlotType.EltCar50kw, true)));
            this.pricingService.updatePricingPolicy(policyConfig.getParkingPolicy());
            this.policyConfig = policyConfig;
            this.hasIntitialzed = true;
            return policyConfig;
        }
        return null;
    }

    public boolean updateParkingPolicy(ParkingPolicy parkingPolicy) {
        if (this.hasIntitialzed) {
            Boolean didUpdate = pricingService.updatePricingPolicy(parkingPolicy);
            if (didUpdate){
                this.policyConfig.setParkingPolicy(pricingService.getParkingPolicy());
            }
            return didUpdate;
        }
        return false;
    }

    public PolicyConfig showParkingPolicy(){
        if (this.hasIntitialzed) {
        return this.policyConfig;
        }
        return null;
    }

    /*
    public Optional<Map> showParkedCars(){
        if (this.hasIntitialzed) {
          Map<String, List<String>> mapParkedCars = new HashMap<>();
        }
        return null;
    }*/

    public synchronized Optional<ParkingSlot> getParkingSlot(String plateNumber, String parkingSlotType){
        if (this.hasIntitialzed && ParkingSlotType.valueOf(parkingSlotType)!= null &&
                parkingBillRepository.findAll().stream().filter(pb -> StringUtils.equalsIgnoreCase(pb.getPlateNumber(), plateNumber)).count()==0){

            Optional<ParkingSlot> freeParkingSlot =
                    parkingSlotRepository.findAll().stream()
                            .filter(ps -> ps.getParkingSlotType().equals(ParkingSlotType.valueOf(parkingSlotType)) && ps.isFree())
                            .findFirst();
            if (freeParkingSlot.isPresent()) {
                ParkingSlot parkingSlot = freeParkingSlot.get();
                parkingSlot.setFree(false);
                parkingSlotRepository.save(parkingSlot);
                parkingBillRepository.save(new ParkingBill(plateNumber, parkingSlot, LocalDateTime.now()));
            }
            return freeParkingSlot;
        }
        return null;
    }

    public synchronized Optional<ParkingBill> leaveParking(String plateNumber) {
        if (this.hasIntitialzed) {
            Optional<ParkingBill> carParkingBill =
                    parkingBillRepository.findAll().stream()
                            .filter(pb -> StringUtils.equalsIgnoreCase(pb.getPlateNumber(), plateNumber) && pb.getEnd() == null)
                            .findFirst();
            if (carParkingBill.isPresent()) {
                ParkingBill parkingBill = carParkingBill.get();
                parkingBill.setEnd(LocalDateTime.now());
                parkingSlotRepository.findById(parkingBill.getParkingSlot().getId()).get().setFree(true);
                pricingService.handleParkingBill(parkingBill);
            }
            return carParkingBill;
        }
        return null;
    }
}
