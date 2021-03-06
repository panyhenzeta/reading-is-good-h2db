package com.getir.readingisgood.service;

import com.getir.readingisgood.exception.CustomValidationException;
import com.getir.readingisgood.model.Book;
import com.getir.readingisgood.model.BookStock;
import com.getir.readingisgood.model.dto.BookDTO;
import com.getir.readingisgood.model.dto.UpdateStockDTO;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.repository.BookStockRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Book service that create and update operations.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookStockRepository bookStockRepository;

    public BookService(BookRepository bookRepository, BookStockRepository bookStockRepository){
        this.bookRepository = bookRepository;
        this.bookStockRepository = bookStockRepository;
    }

    /**
     * method that give monthly statistics.
     *
     * @param book DTO object for book.
     * @return saved object.
     */
    public Book createBook(BookDTO book){
        Book newBook = book.convertBook();
        BookStock bookStock = new BookStock();
        bookStock.setStock(1L);
        newBook.setBookStock(bookStock);
        bookStock.setBook(newBook);
        return bookRepository.save(newBook);
    }

    /**
     * method that update stock of book.
     *
     * @param bookId for existing book.
     * @param stock for increasing or decreasing.
     * @return updated object.
     */
    @Transactional
    public UpdateStockDTO updateStock(Long bookId, Long stock){
        AtomicReference<UpdateStockDTO> updateStockDTO = new AtomicReference<>();
        bookStockRepository.findBookStockByBookId(bookId)
                .ifPresentOrElse(bookStock -> {
                    Long stockOfBook = bookStock.getStock();
                    bookStock.setStock(stockOfBook + stock);
                    BookStock updatedStock = bookStockRepository.save(bookStock);
                    updateStockDTO.set(new UpdateStockDTO(updatedStock));
                }, () -> {
                    throw new CustomValidationException("error.notFoundBook");
                });
        return updateStockDTO.get();
    }

    @Transactional
    public void updateStock(BookStock bookStock){
        bookStockRepository.save(bookStock);
    }

    public Book findBookById(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new CustomValidationException("error.bookNotFound");
        }
    }
}
