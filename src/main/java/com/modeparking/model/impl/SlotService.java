package com.modeparking.model.impl;

import com.modeparking.data.ParkingSlotType;
import com.modeparking.model.ISlotService;

public class SlotService implements ISlotService {

    public ParkingSlotType retrieveParkingSlotType(String plateNumber){
        return ParkingSlotType.EltCar20kw;
    }
}
