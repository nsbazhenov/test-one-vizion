package com.github.nsbazhenov.onevizion.service;

import com.github.nsbazhenov.onevizion.model.BookDto;
import com.github.nsbazhenov.onevizion.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *  Service for processing business logic api query books.
 *
 * @author Bazhenov Nikita
 *
 */
@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public BookDto save(BookDto book) {
        return repository.save(book);
    }

    public List<BookDto> findAllWithAuthorSort() {
        return repository.findAllWithAuthorSort();
    }

    public Map<String, List<BookDto>> findAllGroupedByAuthor() {
        return repository.findAllGroupedByAuthor();
    }

    public List<Map<String, String>> findAllIncludingSymbol(char symbol) {
        return repository.findAllIncludingSymbol(symbol);
    }
}
