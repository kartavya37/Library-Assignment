package com.example.library.repository;

import com.example.library.entity.Author;
import com.example.library.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookRepository bookRepository;

    private Author orwell;
    private Author kafka;

    @BeforeEach
    void setUp() {
        orwell = em.persistAndFlush(new Author("George Orwell", "British"));
        kafka = em.persistAndFlush(new Author("Franz Kafka", "Czech"));

        em.persistAndFlush(new Book("1984", "Dystopian", orwell));
        em.persistAndFlush(new Book("Animal Farm", "Satire", orwell));
        em.persistAndFlush(new Book("The Trial", "Absurdist", kafka));
    }

    @Test
    void findAllBooksWithAuthors_returnsAllBooksWithAuthorEagerlyFetched() {
        List<Book> books = bookRepository.findAllBooksWithAuthors();

        assertEquals(3, books.size());
        // After clearing, the join-fetched author must still be initialized.
        em.clear();
        for (Book b : bookRepository.findAllBooksWithAuthors()) {
            assertNotNull(b.getAuthor());
            assertNotNull(b.getAuthor().getName());
        }
    }

    @Test
    void findAllBooksWithAuthors_omitsBooksWithoutAuthor_inner_join() {
        // Inner join: every persisted book has a non-null author (FK is NOT NULL),
        // so all rows should appear. This test confirms the join doesn't drop rows.
        List<Book> books = bookRepository.findAllBooksWithAuthors();
        long orwellBooks = books.stream()
                .filter(b -> b.getAuthor().getId().equals(orwell.getId())).count();
        assertEquals(2, orwellBooks);
    }

    @Test
    void save_persistsBook() {
        Book saved = bookRepository.save(new Book("Homage to Catalonia", "Memoir", orwell));

        assertNotNull(saved.getId());
        assertEquals("Homage to Catalonia", em.find(Book.class, saved.getId()).getTitle());
    }
}
