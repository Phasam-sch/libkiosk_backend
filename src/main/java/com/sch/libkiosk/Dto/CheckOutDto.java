package com.sch.libkiosk.Dto;

import com.sch.libkiosk.Entity.Book;
import com.sch.libkiosk.Entity.CheckOut;
import com.sch.libkiosk.Entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CheckOutDto {

    private User user;

    private Book book;

    private LocalDateTime checkedOutDate;

    private LocalDateTime returnDate;

    private Boolean isReturned;

    public CheckOut toEntity(){
        return CheckOut.builder()
                .user(user)
                .book(book)
                .checkedOutDate(checkedOutDate)
                .returnDate(returnDate)
                .isReturned(isReturned)
                .build();
    }

    @Builder
    public CheckOutDto(User user, Book book, LocalDateTime checkedOutDate, LocalDateTime returnDate, Boolean isReturned){
        this.user = user;
        this.book = book;
        this.checkedOutDate = checkedOutDate;
        this.returnDate = returnDate;
        this.isReturned = isReturned;
    }
}
