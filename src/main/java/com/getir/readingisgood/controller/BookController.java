package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.Book;
import com.getir.readingisgood.model.dto.BookDTO;
import com.getir.readingisgood.model.dto.UpdateStockDTO;
import com.getir.readingisgood.model.response.SuccessResponse;
import com.getir.readingisgood.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping("/books")
    public ResponseEntity<SuccessResponse<Book>> createBook(@RequestBody BookDTO book) {
        Book savedBook = bookService.createBook(book);
        return ResponseEntity.ok().body(new SuccessResponse<>(savedBook));
    }

    @PutMapping("/book/update-stock")
    public ResponseEntity<SuccessResponse<UpdateStockDTO>> updateStock(@RequestParam Long bookId, @RequestParam Long stock){
        UpdateStockDTO updateStock = bookService.updateStock(bookId, stock);
        return ResponseEntity.ok().body(new SuccessResponse<>(updateStock));
    }

}
