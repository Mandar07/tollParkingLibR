package com.modeparking.config;

import com.modeparking.data.ParkingPolicy;
import org.springframework.beans.factory.annotation.Value;

public class PolicyConfig {
    @Value("{parking.numStdParkingSlotValue}")
    private int numStdParkingSlot;
    @Value("{parking.numEltCar20kwParkingSlotValue}")
    private int numEltCar20kwParkingSlot;
    @Value("{parking.numEltCar50kwParkingSlotValue}")
    private int numEltCar50kwParkingSlot;

    private ParkingPolicy parkingPolicy;

    public PolicyConfig(
            int numStdParkingSlot,
            int numEltCar20kwParkingSlot,
            int numEltCar50kwParkingSlot,
            ParkingPolicy parkingPolicy) {
        this.numStdParkingSlot = numStdParkingSlot;
        this.numEltCar20kwParkingSlot = numEltCar20kwParkingSlot;
        this.numEltCar50kwParkingSlot = numEltCar50kwParkingSlot;
        this.parkingPolicy = parkingPolicy;
    }

    public int getNumStdParkingSlot() {
        return numStdParkingSlot;
    }

    public int getNumEltCar20kwParkingSlot() {
        return numEltCar20kwParkingSlot;
    }

    public int getNumEltCar50kwParkingSlot() {
        return numEltCar50kwParkingSlot;
    }

    public ParkingPolicy getParkingPolicy() {
        return parkingPolicy;
    }

    public void setParkingPolicy(ParkingPolicy parkingPolicy) {
        this.parkingPolicy = parkingPolicy;
    }
}
