package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.exception.DatabaseException;
import com.example.library.repository.BookRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooksWithAuthors() {
        return bookRepository.findAllBooksWithAuthors();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new DatabaseException("Book not found with id: " + id));
    }

    public Book save(Book book) {
        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Failed to save book: constraint violation.", e);
        }
    }

    public Book update(Long id, Book updatedBook) {
        Book existing = findById(id);
        existing.setTitle(updatedBook.getTitle());
        existing.setGenre(updatedBook.getGenre());
        existing.setAuthor(updatedBook.getAuthor());
        try {
            return bookRepository.save(existing);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Failed to update book: constraint violation.", e);
        }
    }
}
