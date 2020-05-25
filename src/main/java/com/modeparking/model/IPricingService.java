package com.modeparking.model;

import com.modeparking.data.ParkingBill;
import com.modeparking.data.ParkingPolicy;

public interface IPricingService {
    /*
     * Update the price policy which is initialized with parking configuration.
     * @param pricingPolicy
     * @return true if succees, false if invalid
     */
    boolean updatePricingPolicy(ParkingPolicy parkingPolicy);
    /*
     * Calculate the price for the parking and return the updated parking bill.
     * @param parkingBill
     * @return return parking bill after setting the price
     */
    ParkingBill handleParkingBill(ParkingBill parkingBill);
}
