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
    private Long ISBN;

    @Column(nullable = false)
    private Integer pageNum;

    @Column(nullable = false)
    private Long rfidId;

    @Column(nullable = false)
    private Boolean isCheckedOut;


    @Builder
    public Book(String title, String author, String publisher, Long ISBN, Integer pageNum, Long rfidId, Boolean isCheckedOut){
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.ISBN = ISBN;
        this.pageNum = pageNum;
        this.rfidId = rfidId;
        this.isCheckedOut = isCheckedOut;
    }

}
