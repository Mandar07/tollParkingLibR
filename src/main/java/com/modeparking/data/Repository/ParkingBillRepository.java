package com.modeparking.data.Repository;

import com.modeparking.data.ParkingBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingBillRepository extends JpaRepository<ParkingBill, Long> {
}
