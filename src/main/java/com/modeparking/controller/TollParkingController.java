package com.modeparking.controller;

import com.modeparking.config.PolicyConfig;
import com.modeparking.data.ParkingBill;
import com.modeparking.data.ParkingPolicy;
import com.modeparking.model.ITollParkingAgent;
import com.modeparking.data.ParkingSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
public class TollParkingController {

    private ITollParkingAgent tollParkingAgent;

    @Autowired
    public TollParkingController (ITollParkingAgent tollParkingAgent) {
        this.tollParkingAgent = tollParkingAgent;
    }

    @PostMapping("/initialize")
    public ResponseEntity<PolicyConfig> initializePolicyConfig(@RequestBody PolicyConfig policyConfig) throws Exception{
     PolicyConfig pc = Optional.ofNullable(tollParkingAgent.initialize(policyConfig)).orElseThrow(() -> new Exception("Toll parking Configuration has been already initialized"));
     return ResponseEntity.ok(pc);
    }

    @PutMapping("/pricingpolicy")
    public ResponseEntity<ParkingPolicy> updatePricingPolicy(@RequestBody ParkingPolicy parkingPolicy) throws Exception {
        if(tollParkingAgent.updateParkingPolicy(parkingPolicy)){
            return ResponseEntity.ok(parkingPolicy);
        }
        else {
            throw new Exception("Error : Updating Pricing policy !!");
        }
    }

    @GetMapping("/showparkingpolicy")
    public ResponseEntity<PolicyConfig> showparkingpolicy() throws Exception{
        PolicyConfig pc = Optional.ofNullable(tollParkingAgent.showParkingPolicy()).orElseThrow(() -> new Exception("Toll parking Configuration has not been initialized "));
        return ResponseEntity.ok(pc);
    }
    @GetMapping("/entryparking/{plateNumber}/{parkingSlotType}")
    public ResponseEntity<ParkingSlot> enterParking(@PathVariable("plateNumber") String plateNumber, @PathVariable("parkingSlotType") String parkingSlotType)
    throws Exception{
        ParkingSlot parkingSlot = tollParkingAgent.getParkingSlot(plateNumber, parkingSlotType)
                .orElseThrow(() -> new Exception("Error : Cannot enter the parking!!"));
        parkingSlot.setFree(true);
        return ResponseEntity.ok(parkingSlot);
    }

    @GetMapping("/exitparking/{plateNumber}")
    public ResponseEntity<ParkingBill> leaveParking(@PathVariable("plateNumber") String plateNumber) throws Exception{
        ParkingBill parkingBill = tollParkingAgent.leaveParking(plateNumber).orElseThrow(() -> new Exception("No parking bill has been found for this car: " + plateNumber));
        return ResponseEntity.ok(parkingBill);
    }

}
