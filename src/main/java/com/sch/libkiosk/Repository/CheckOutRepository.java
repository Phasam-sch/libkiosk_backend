package com.sch.libkiosk.Repository;

import com.sch.libkiosk.Entity.CheckOut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckOutRepository extends JpaRepository<CheckOut, Long> {
}
