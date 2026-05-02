package com.example.library.service;

import com.example.library.entity.Author;
import com.example.library.exception.DatabaseException;
import com.example.library.repository.AuthorRepository;
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
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("George Orwell", "British");
        author.setId(1L);
    }

    @Test
    void findAll_returnsAuthors() {
        when(authorRepository.findAll()).thenReturn(List.of(author));

        assertEquals(1, authorService.findAll().size());
        verify(authorRepository).findAll();
    }

    @Test
    void findById_existing_returnsAuthor() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Author found = authorService.findById(1L);

        assertNotNull(found);
        assertEquals("George Orwell", found.getName());
    }

    @Test
    void findById_missing_throwsDatabaseException() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(DatabaseException.class, () -> authorService.findById(99L));
    }

    @Test
    void save_valid_returnsSaved() {
        when(authorRepository.save(author)).thenReturn(author);

        Author saved = authorService.save(author);

        assertEquals("George Orwell", saved.getName());
        verify(authorRepository).save(author);
    }

    @Test
    void save_constraintViolation_throwsDatabaseException() {
        when(authorRepository.save(author))
                .thenThrow(new DataIntegrityViolationException("unique"));

        assertThrows(DatabaseException.class, () -> authorService.save(author));
    }

    @Test
    void update_existing_updatesFields() {
        Author update = new Author("Eric Blair", "British");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenAnswer(inv -> inv.getArgument(0));

        Author result = authorService.update(1L, update);

        assertEquals("Eric Blair", result.getName());
    }

    @Test
    void update_constraintViolation_throwsDatabaseException() {
        Author update = new Author("Eric Blair", "British");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class)))
                .thenThrow(new DataIntegrityViolationException("unique"));

        assertThrows(DatabaseException.class, () -> authorService.update(1L, update));
    }
}
