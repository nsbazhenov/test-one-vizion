package com.github.nsbazhenov.onevizion.controller;

import com.github.nsbazhenov.onevizion.model.BookDto;
import com.github.nsbazhenov.onevizion.service.BookService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    private static Map<String, List<BookDto>> mapBooks;
    public static List<BookDto> bookList;
    public static BookDto book;
    private static List<Map<String, String>> listCharBooks;

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @BeforeClass
    public static void runOnceBeforeClass() {
        mapBooks = new HashMap<>();
        bookList = new ArrayList<>();
        book = new BookDto(1, "title", "author", "description");
        bookList.add(book);
        mapBooks.put("author", bookList);

        listCharBooks = new ArrayList<>();
        HashMap<String, String> hashMapSymbol = new HashMap<>();
        hashMapSymbol.put("author", "1");
        listCharBooks.add(hashMapSymbol);
    }

    @Test
    public void givenBooksGroupedByAuthor_whenGetBooks_thenStatus200() {
        when(bookService.findAllGroupedByAuthor()).thenReturn(mapBooks);
        ResponseEntity<Map<String, List<BookDto>>> allGroupedByAuthor = bookController.findAllGroupedByAuthor();
        Assert.assertEquals(allGroupedByAuthor.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(Objects.requireNonNull(allGroupedByAuthor.getBody()).size(), mapBooks.size());
    }

    @Test
    public void givenBooksSortedByAuthor_whenGetBooks_thenStatus200() {
        when(bookService.findAllWithAuthorSort()).thenReturn(bookList);
        ResponseEntity<List<BookDto>> allWithAuthorSort = bookController.findAllWithAuthorSort();
        Assert.assertEquals(allWithAuthorSort.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(Objects.requireNonNull(allWithAuthorSort.getBody()).size(), mapBooks.size());
    }

    @Test
    public void givenAuthorIncludingSymbol_whenCountSymbol_thenStatus200() {
        when(bookService.findAllIncludingSymbol('e')).thenReturn(listCharBooks);
        ResponseEntity<List<Map<String, String>>> findAllIncludingSymbol = bookController.findAllIncludingSymbol('e');
        Assert.assertEquals(findAllIncludingSymbol.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(Objects.requireNonNull(findAllIncludingSymbol.getBody()).size(), mapBooks.size());
    }

    @Test
    public void saveBook_whenGetBook_thenStatus200() {
        when(bookService.save(book)).thenReturn(book);
        ResponseEntity<BookDto> bookFromDb = bookController.save(book);
        Assert.assertEquals(bookFromDb.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(Objects.requireNonNull(bookFromDb.getBody()).author(), book.author());
        Assert.assertEquals(Objects.requireNonNull(bookFromDb.getBody()).title(), book.title());
        Assert.assertEquals(Objects.requireNonNull(bookFromDb.getBody().description()), book.description());
    }
}
