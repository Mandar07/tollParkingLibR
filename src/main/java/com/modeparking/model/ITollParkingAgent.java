package com.modeparking.model;

import com.modeparking.config.PolicyConfig;
import com.modeparking.data.ParkingBill;
import com.modeparking.data.ParkingPolicy;
import com.modeparking.data.ParkingSlot;

import java.util.Optional;

public interface ITollParkingAgent {
    /*
     * Initialize the toll parking's configuration. The configuration registers total number of
     * parking slots for the types with their price policy.
     * @param policyConfig
     * @return initialized policyConfig
     */
    PolicyConfig initialize(PolicyConfig policyConfig);
    /*
     * Update the parking price policy already initialized with toll parking configuration.
     * @param parkingPolicy
     * @return true if sucess, false if invalid
     */
    boolean updateParkingPolicy(ParkingPolicy parkingPolicy);

    /*
     * Show the parking price policy already initialized
     * @return policyConfig if exists, else null
     */
    PolicyConfig showParkingPolicy();
    /*
     * Get the first disponible slot from parking
     * @param plateNumber, parkingSlotType
     * @return parking slot is returned if disponible
     */
    Optional<ParkingSlot> getParkingSlot(String plateNumber, String parkingSlotType);
    /*
     * The slot is left, and the parking bill is calculated for the car.
     * @param plateNumber
     * @return parking bill
     */
    Optional<ParkingBill> leaveParking(String plateNumber);

    /*
     * Show list of current parked cars in parking area.
     * @param plateNumber
     * @return Map of parking type and car plateNumber
     */
    //Optional<Map> showParkedCars();
}
