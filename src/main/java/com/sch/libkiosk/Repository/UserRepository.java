package com.sch.libkiosk.Repository;

import com.sch.libkiosk.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
