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
import org.hibernate.annotations.Check;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
                .rfidNum(book.getRfidNum())
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
                    .rfidNum(book.getRfidNum())
                    .isCheckedOut(book.getIsCheckedOut())
                    .build();

            retList.add(dto);
        }

        return retList;
    }


    //TODO:책 정보 업데이트(rfid수정)
    @Transactional
    public void updateBook(Long id, BookDto dto) throws EntityNotFoundException {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if(optionalBook.isPresent()){
            Book book = optionalBook.get();
            book.updateBook(dto.getRfidNum());
        }else{
            throw new EntityNotFoundException("존재하지 않는 도서");
        }
    }

    //대출 생성
    @Transactional
    public void postCheckOut(CheckOutDto dto) throws EntityNotFoundException, IllegalAccessException{
        Optional<Book> optionalBook = bookRepository.findById(dto.getBook().getId());

        if(optionalBook.isPresent()){
            Book book = optionalBook.get();
            //자동으로 대출일, 반납일 설정 후 반환
            if(!book.getIsCheckedOut()){
                CheckOutDto saveDto = CheckOutDto.builder()
                        .book(dto.getBook())
                        .user(dto.getUser())
                        .checkedOutDate(LocalDateTime.now())
                        .returnDate(LocalDateTime.now().plusDays(7))
                        .isReturned(false)
                        .build();
                CheckOut checkout = dto.toEntity();
                checkout.setCheckOutInit();
                checkOutRepository.save(checkout);
                book.checkout();
            }else{
                throw new IllegalAccessException("이미 대출된 도서");
            }
        }else{
            throw new EntityNotFoundException("존재하지 않는 도서");
        }
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
