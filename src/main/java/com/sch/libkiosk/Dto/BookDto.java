package com.sch.libkiosk.Dto;

import com.sch.libkiosk.Entity.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Data;

@Data
public class BookDto {

    private String title;

    private String author;

    private String publisher;

    private String ISBN;

    private Integer pageNum;

    private Long rfidNum;

    private Boolean isCheckedOut;

    public Book toEntity(){
        return Book.builder()
                .title(title)
                .author(author)
                .publisher(publisher)
                .ISBN(ISBN)
                .pageNum(pageNum)
                .rfidNum(rfidNum)
                .isCheckedOut(isCheckedOut)
                .build();
    }

    @Builder
    public BookDto(String title, String author, String publisher, String ISBN, Integer pageNum, Long rfidNum
            , Boolean isCheckedOut){
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.ISBN = ISBN;
        this.pageNum = pageNum;
        this.rfidNum = rfidNum;
        this.isCheckedOut = isCheckedOut;
    }
}
