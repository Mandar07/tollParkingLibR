package com.modeparking.data;

public enum ParkingSlotType {
    STANDARD(0) {
        @Override
        public boolean isForEltCar(){return false;}
    },
    EltCar20kw(20){@Override
    public boolean isForEltCar(){return true;}},
    EltCar50kw(50){@Override
    public boolean isForEltCar(){return true;}};

    private int voltSupply;
    public int getVoltSupply() {
        return voltSupply;
    }

    ParkingSlotType(int voltSupply) {this.voltSupply  = voltSupply;}
    public boolean isForEltCar() {
        return false;
    }
}
