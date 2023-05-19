package com.sch.libkiosk.Service;

import com.sch.libkiosk.Dto.BookDto;
import com.sch.libkiosk.Dto.CheckOutDto;
import com.sch.libkiosk.Entity.Book;
import com.sch.libkiosk.Entity.CheckOut;
import com.sch.libkiosk.Repository.BookRepository;
import com.sch.libkiosk.Repository.CheckOutRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final CheckOutRepository checkOutRepository;
    //책 등록
    @Transactional
    public void postBook(BookDto bookDto){
        bookRepository.save(bookDto.toEntity());
    }

    //책 정보 불러오기
    @Transactional
    public BookDto readBookById(Long id) throws EntityNotFoundException{
        Optional<Book> optionalBook = bookRepository.findById(id);

        if(optionalBook.isPresent()){
            Book book = optionalBook.get();

            return BookDto.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .ISBN(book.getISBN())
                .isCheckedOut(book.getIsCheckedOut())
                .pageNum(book.getPageNum())
                .rfidId(book.getRfidId())
                .build();
        }else{
            throw new EntityNotFoundException();
        }
    }


    //책 목록 불러오기
    @Transactional
    public List<BookDto> getAllBookList(){
        List<Book> bookList = bookRepository.findAll();
        List<BookDto> retList = new ArrayList<>();

        for (Book book: bookList ) {
            BookDto dto = BookDto.builder()
                    .title(book.getTitle())
                    .author(book.getAuthor())
                    .publisher(book.getPublisher())
                    .ISBN(book.getISBN())
                    .pageNum(book.getPageNum())
                    .rfidId(book.getRfidId())
                    .isCheckedOut(book.getIsCheckedOut())
                    .build();

            retList.add(dto);
        }

        return retList;
    }


    //TODO:책 정보 업데이트

    //대출 생성
    @Transactional
    public void postCheckOut(CheckOutDto dto){
        CheckOut checkout = dto.toEntity();
        checkout.setCheckOutInit();
        checkOutRepository.save(checkout);
    }


    //반납 처리
    @Transactional
    public void returnBook(Long id) throws EntityNotFoundException, IllegalAccessException{
        Optional<CheckOut> optionalCheckOut = checkOutRepository.findById(id);

        if(optionalCheckOut.isPresent()){
             CheckOut checkOut = optionalCheckOut.get();
             if(checkOut.getIsReturned()){
                 throw new IllegalAccessException("이미 처리된 반납입니다.");
             }else{
                 checkOut.setReturn();
             }
        }else{
            throw new EntityNotFoundException();
        }
    }


}
