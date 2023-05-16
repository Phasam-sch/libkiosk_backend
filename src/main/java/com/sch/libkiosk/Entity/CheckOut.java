package com.sch.libkiosk.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class CheckOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false, name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(nullable = false, name = "bookId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @Column(nullable = false)
    private LocalDateTime checkedOutDate;

    @Column(nullable = false)
    private LocalDateTime returnDate;

    @Column(nullable = false)
    private Boolean isReturned;

    public void setCheckOutInit(){
        this.isReturned = false;
        this.checkedOutDate = LocalDateTime.now();
        this.returnDate = this.checkedOutDate.plusDays(7);
    }

    public void setReturn(){
        this.isReturned = true;
    }

    @Builder
    public CheckOut (User user, Book book, LocalDateTime checkedOutDate, LocalDateTime returnDate, Boolean isReturned){
        this.user = user;
        this.book = book;
        this.checkedOutDate = checkedOutDate;
        this.returnDate = returnDate;
        this.isReturned = isReturned;
    }
}
