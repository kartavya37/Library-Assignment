package com.example.library;

import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public DataInitializer(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        List<Author> authors = authorRepository.saveAll(List.of(
                new Author("George Orwell", "British"),
                new Author("Gabriel García Márquez", "Colombian"),
                new Author("Toni Morrison", "American"),
                new Author("Haruki Murakami", "Japanese"),
                new Author("Chimamanda Ngozi Adichie", "Nigerian"),
                new Author("Franz Kafka", "Czech"),
                new Author("Virginia Woolf", "British"),
                new Author("Leo Tolstoy", "Russian"),
                new Author("Fyodor Dostoevsky", "Russian"),
                new Author("Jane Austen", "British")
        ));

        bookRepository.saveAll(List.of(
                new Book("1984", "Dystopian Fiction", authors.get(0)),
                new Book("One Hundred Years of Solitude", "Magical Realism", authors.get(1)),
                new Book("Beloved", "Historical Fiction", authors.get(2)),
                new Book("Norwegian Wood", "Literary Fiction", authors.get(3)),
                new Book("Purple Hibiscus", "Literary Fiction", authors.get(4)),
                new Book("The Metamorphosis", "Absurdist Fiction", authors.get(5)),
                new Book("Mrs Dalloway", "Modernist Fiction", authors.get(6)),
                new Book("War and Peace", "Historical Fiction", authors.get(7)),
                new Book("Crime and Punishment", "Psychological Fiction", authors.get(8)),
                new Book("Pride and Prejudice", "Romance", authors.get(9))
        ));
    }
}
