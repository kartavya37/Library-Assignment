package com.example.library.service;

import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.exception.DatabaseException;
import com.example.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Author author;
    private Book book;

    @BeforeEach
    void setUp() {
        author = new Author("George Orwell", "British");
        author.setId(1L);

        book = new Book("1984", "Dystopian Fiction", author);
        book.setId(1L);
    }

    @Test
    void findAllBooksWithAuthors_returnsAllBooks() {
        when(bookRepository.findAllBooksWithAuthors()).thenReturn(List.of(book));

        List<Book> result = bookService.findAllBooksWithAuthors();

        assertEquals(1, result.size());
        assertEquals("1984", result.get(0).getTitle());
        verify(bookRepository).findAllBooksWithAuthors();
    }

    @Test
    void findById_existingId_returnsBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.findById(1L);

        assertNotNull(result);
        assertEquals("1984", result.getTitle());
    }

    @Test
    void findById_nonExistingId_throwsDatabaseException() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(DatabaseException.class, () -> bookService.findById(99L));
    }

    @Test
    void save_validBook_returnsSavedBook() {
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.save(book);

        assertNotNull(result);
        assertEquals("1984", result.getTitle());
        verify(bookRepository).save(book);
    }

    @Test
    void save_constraintViolation_throwsDatabaseException() {
        when(bookRepository.save(book)).thenThrow(new DataIntegrityViolationException("Constraint"));

        assertThrows(DatabaseException.class, () -> bookService.save(book));
    }

    @Test
    void update_existingBook_updatesFields() {
        Book updatedDetails = new Book("Animal Farm", "Political Satire", author);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        Book result = bookService.update(1L, updatedDetails);

        assertEquals("Animal Farm", result.getTitle());
        assertEquals("Political Satire", result.getGenre());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void update_constraintViolation_throwsDatabaseException() {
        Book updatedDetails = new Book("Animal Farm", "Political Satire", author);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenThrow(new DataIntegrityViolationException("Constraint"));

        assertThrows(DatabaseException.class, () -> bookService.update(1L, updatedDetails));
    }
}
