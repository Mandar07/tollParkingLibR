package com.modeparking.data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ParkingBill")
public class ParkingBill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "plate_number", nullable = false)
    private String plateNumber;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime start;

    @Column(name = "end_time", nullable = true)
    private LocalDateTime end;

    @ManyToOne
    @JoinColumn(name = "ParkingSlot_ID")
    private ParkingSlot parkingSlot;

    public ParkingBill() {
    }

    public ParkingBill(String plateNumber, ParkingSlot parkingSlot, LocalDateTime start) {
        this.plateNumber = plateNumber;
        this.parkingSlot = parkingSlot;
        this.start = start;
        this.end = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    public void setParkingSlot(ParkingSlot parkingSlot) {
        this.parkingSlot = parkingSlot;
    }
}
