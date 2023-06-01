package com.sch.libkiosk.Repository;

import com.sch.libkiosk.Entity.UserPics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserPicsRepository extends JpaRepository<UserPics, Long> {
    List<UserPics> findByUserId(Long userId);
}
