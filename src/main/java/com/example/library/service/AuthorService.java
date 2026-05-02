package com.example.library.service;

import com.example.library.entity.Author;
import com.example.library.exception.DatabaseException;
import com.example.library.repository.AuthorRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public List<Author> findAllAuthorsWithBooks() {
        return authorRepository.findAllAuthorsWithBooks();
    }

    public Author findById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new DatabaseException("Author not found with id: " + id));
    }

    public Author save(Author author) {
        try {
            return authorRepository.save(author);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Failed to save author: name must be unique.", e);
        }
    }

    public Author update(Long id, Author updated) {
        Author existing = findById(id);
        existing.setName(updated.getName());
        existing.setNationality(updated.getNationality());
        try {
            return authorRepository.save(existing);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Failed to update author: constraint violation.", e);
        }
    }
}
