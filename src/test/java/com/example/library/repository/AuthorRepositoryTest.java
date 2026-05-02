package com.example.library.repository;

import com.example.library.entity.Author;
import com.example.library.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void findAllAuthorsWithBooks_returnsOnlyAuthorsWhoHaveBooks() {
        Author orwell = em.persistAndFlush(new Author("George Orwell", "British"));
        Author kafka = em.persistAndFlush(new Author("Franz Kafka", "Czech"));
        em.persistAndFlush(new Author("Lonely Author", "Atlantean")); // no books

        em.persistAndFlush(new Book("1984", "Dystopian", orwell));
        em.persistAndFlush(new Book("The Trial", "Absurdist", kafka));

        List<Author> authors = authorRepository.findAllAuthorsWithBooks();

        // Inner join must exclude the bookless author.
        assertEquals(2, authors.size());
        assertTrue(authors.stream().anyMatch(a -> a.getName().equals("George Orwell")));
        assertTrue(authors.stream().anyMatch(a -> a.getName().equals("Franz Kafka")));
        assertTrue(authors.stream().noneMatch(a -> a.getName().equals("Lonely Author")));
    }

    @Test
    void save_duplicateName_throwsDataIntegrityViolation() {
        authorRepository.saveAndFlush(new Author("Unique Name", "Earth"));

        assertThrows(DataIntegrityViolationException.class,
                () -> authorRepository.saveAndFlush(new Author("Unique Name", "Mars")));
    }
}
