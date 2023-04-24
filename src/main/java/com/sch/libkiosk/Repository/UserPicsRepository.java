package com.sch.libkiosk.Repository;

import com.sch.libkiosk.Entity.UserPics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPicsRepository extends JpaRepository<UserPics, Long> {
    UserPics findByUserId(Long userId);
}
