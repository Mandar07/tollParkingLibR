package com.modeparking.model;

import com.modeparking.data.ParkingSlotType;

public interface ISlotService {
    /*
     * Get type of the car by its plate number and return slot type.
     *
     * @param plateNumber
     * @return return an appropriate parking slot type
     */
    ParkingSlotType retrieveParkingSlotType(String plateNumber);
}
