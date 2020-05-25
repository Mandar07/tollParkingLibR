package com.modeparking.model.impl;

import com.modeparking.data.ParkingBill;
import com.modeparking.data.ParkingPolicy;
import com.modeparking.data.Repository.ParkingBillRepository;
import com.modeparking.model.IPricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
public class PricingService implements IPricingService {

    private ParkingPolicy parkingPolicy;
    private ParkingBillRepository parkingBillRepository;

    @Autowired
    public PricingService(ParkingBillRepository parkingBillRepository){
        this.parkingBillRepository = parkingBillRepository;
    }

    public boolean updatePricingPolicy(ParkingPolicy parkingPolicy) {
        if (parkingPolicy.getFixedAmt() >= 0 && parkingPolicy.getHourPrice() > 0) {
            this.parkingPolicy = parkingPolicy;
            return true;
        }
        return false;
    }

    public ParkingBill handleParkingBill(ParkingBill parkingBill) {
        double price = parkingPolicy.getFixedAmt() + parkingPolicy.getHourPrice() * parkingHours(parkingBill);
        parkingBill.setPrice(price);
        return parkingBillRepository.save(parkingBill);
    }

    private double parkingHours(ParkingBill parkingBill) {
        return parkingBill.getStart().until(parkingBill.getEnd(), ChronoUnit.MINUTES) / 60.0;
    }

    public ParkingPolicy getParkingPolicy() {
        return parkingPolicy;
    }

}
