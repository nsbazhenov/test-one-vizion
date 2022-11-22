package com.github.nsbazhenov.onevizion.controller;

import com.github.nsbazhenov.onevizion.model.BookDto;
import com.github.nsbazhenov.onevizion.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *  Controller for handling api book requests.
 *
 * @author Bazhenov Nikita
 *
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<BookDto> save(@RequestBody BookDto book) {
        return ResponseEntity.ok(service.save(book));
    }

    @GetMapping(value = "/all-with-author-sorting")
    public ResponseEntity<List<BookDto>> findAllWithAuthorSort() {
        return ResponseEntity.ok(service.findAllWithAuthorSort());
    }

    @GetMapping(value = "/all-grouped-by-author")
    public ResponseEntity<Map<String, List<BookDto>>> findAllGroupedByAuthor() {
        return ResponseEntity.ok(service.findAllGroupedByAuthor());
    }

    @GetMapping(value = "/including-symbol-by-title")
    public ResponseEntity<List<Map<String, String>>> findAllIncludingSymbol(@RequestParam char symbol) {
        return ResponseEntity.ok(service.findAllIncludingSymbol(symbol));
    }
}
