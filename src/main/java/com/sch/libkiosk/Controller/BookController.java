package com.sch.libkiosk.Controller;

import com.sch.libkiosk.Dto.BookDto;
import com.sch.libkiosk.Dto.CheckOutDto;
import com.sch.libkiosk.Service.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.InputMismatchException;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> PostBook(@RequestBody BookDto bookDto){
            bookService.postBook(bookDto);
            return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long id){
        HttpHeaders headers = new HttpHeaders();
        BookDto responseDto;
        try{
            responseDto = bookService.readBookById(id);
        }catch(EntityNotFoundException e){
            log.error("Book not Exist");
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<BookDto>(responseDto, headers, HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @GetMapping("/getAll")
    public List<BookDto> getAllBook(){
        return bookService.getAllBookList();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable("id")Long id, @RequestBody BookDto dto){
        try{
            bookService.updateBook(id, dto);
            return ResponseEntity.ok().build();
        }catch(EntityNotFoundException e){
            log.error("존재하지 않는 도서");
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> PostCheckOut(@RequestBody CheckOutDto checkOutDto){
        try{
            bookService.postCheckOut(checkOutDto);
        }catch(Exception e){
            log.error("fail to checkout");
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

//    TODO: returnBook에 대한 return DTO 만들어 처리할 수 있도록 만들 것.
//    @PatchMapping("/return")
//    public ResponseEntity<?> returnBook()

}

