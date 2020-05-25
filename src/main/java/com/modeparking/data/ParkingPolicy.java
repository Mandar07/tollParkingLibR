package com.modeparking.data;

import org.springframework.beans.factory.annotation.Value;

public class ParkingPolicy {
    @Value("{parking.fixedAmnt}")
    private double fixedAmt;
    @Value("{parking.hourPrice}")
    private double hourPrice;

    public ParkingPolicy(double fixedAmt, double hourPrice) {
        this.fixedAmt = fixedAmt;
        this.hourPrice = hourPrice;
    }
    public double getFixedAmt() {
        return fixedAmt;
    }
    public double getHourPrice() {
        return hourPrice;
    }
}
