package com.sch.libkiosk.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String ISBN;

    @Column(nullable = false)
    private Integer pageNum;

    @Column(nullable = false)
    private Long rfidNum;

    @Column(nullable = false)
    private Boolean isCheckedOut;


    public void checkout(){
        this.isCheckedOut = true;
    }

    public void returnBook() {
        this.isCheckedOut = false;
    }

    public void updateBook(Long rfidNum){
        this.rfidNum = rfidNum;
    }

    @Builder
    public Book(String title, String author, String publisher, String ISBN, Integer pageNum, Long rfidNum, Boolean isCheckedOut){
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.ISBN = ISBN;
        this.pageNum = pageNum;
        this.rfidNum = rfidNum;
        this.isCheckedOut = isCheckedOut;
    }

}
