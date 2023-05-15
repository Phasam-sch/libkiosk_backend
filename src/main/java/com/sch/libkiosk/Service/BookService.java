package com.sch.libkiosk.Service;

import com.sch.libkiosk.Dto.BookDto;
import com.sch.libkiosk.Repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    //책 등록
    @Transactional
    public void postBook(BookDto bookDto){
        bookRepository.save(bookDto.toEntity());
    }


}
