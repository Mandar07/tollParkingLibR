package com.modeparking.data;

import javax.persistence.*;

@Entity
@Table(name = "ParkingSlot")
public class ParkingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private ParkingSlotType parkingSlotType;

    @Column(name = "free", nullable = false)
    private boolean free;

    public ParkingSlot() {
    }
    public ParkingSlot(ParkingSlotType parkingSlotType, boolean free) {
        this.parkingSlotType = parkingSlotType;
        this.free = free;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ParkingSlotType getParkingSlotType() {
        return parkingSlotType;
    }

    public void setParkingSlotType(ParkingSlotType parkingSlotType) {
        this.parkingSlotType = parkingSlotType;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }
}
