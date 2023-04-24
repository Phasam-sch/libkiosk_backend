package com.sch.libkiosk.Repository;

import com.sch.libkiosk.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
