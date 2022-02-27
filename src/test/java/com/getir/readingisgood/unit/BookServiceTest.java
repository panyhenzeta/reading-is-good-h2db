package com.getir.readingisgood.unit;

import com.getir.readingisgood.model.Book;
import com.getir.readingisgood.model.BookStock;
import com.getir.readingisgood.model.dto.BookDTO;
import com.getir.readingisgood.model.dto.UpdateStockDTO;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.repository.BookStockRepository;
import com.getir.readingisgood.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
@AutoConfigureTestDatabase
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookStockRepository bookStockRepository;

    private Book book;
    private BookStock bookStock;

    @Before
    public void init(){
        this.book = new Book();
        this.book.setName("Getir");
        this.book.setPrice(25.0);

        this.bookStock = new BookStock(5L);
        this.book.setBookStock(bookStock);
        this.bookStock.setBook(book);
    }


    @Test
    public void whenSaveBook_shouldReturnBook() {
        when(bookRepository.save(ArgumentMatchers.any(Book.class))).thenReturn(book);
        Book newBook = bookService.createBook(new BookDTO(book));

        assertThat(book.getName()).isSameAs(newBook.getName());
        verify(bookRepository).save(ArgumentMatchers.any(Book.class));
    }

    @Test
    public void whenFindBookById_shouldReturnBook() {
        when(bookRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(book));

        Book foundBook = bookService.findBookById(ArgumentMatchers.any(Long.class));

        assertThat(foundBook.getName()).isSameAs(book.getName());
        verify(bookRepository).findById(ArgumentMatchers.any(Long.class));
    }

    @Test
    public void whenUpdateStock_shouldReturnNewUpdatedStockBook() {
        when(bookStockRepository.findBookStockByBookId(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(bookStock));
        when(bookStockRepository.save(ArgumentMatchers.any(BookStock.class))).thenReturn(bookStock);

        Long stock = Double.valueOf(Math.random() * 10).longValue();
        Long id = Double.valueOf(Math.random() * 10).longValue();
        UpdateStockDTO updatedBook = bookService.updateStock(id, stock) ;

        assertThat(bookStock.getStock()).isEqualTo(updatedBook.getStock());
        verify(bookStockRepository).findBookStockByBookId(ArgumentMatchers.any(Long.class));
    }

}
