package com.github.nsbazhenov.onevizion.service;

import com.github.nsbazhenov.onevizion.controller.BookController;
import com.github.nsbazhenov.onevizion.model.BookDto;
import com.github.nsbazhenov.onevizion.repository.BookRepository;
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
public class BookServiceTest {

    private static Map<String, List<BookDto>> mapBooks;
    public static List<BookDto> bookList;
    public static BookDto book;
    private static List<Map<String, String>> listCharBooks;

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

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
        when(bookRepository.findAllGroupedByAuthor()).thenReturn(mapBooks);
        Map<String, List<BookDto>> allGroupedByAuthor = bookService.findAllGroupedByAuthor();
        Assert.assertEquals(allGroupedByAuthor.size(), mapBooks.size());
    }

    @Test
    public void givenBooksSortedByAuthor_whenGetBooks_thenStatus200() {
        when(bookRepository.findAllWithAuthorSort()).thenReturn(bookList);
        List<BookDto> allWithAuthorSort = bookService.findAllWithAuthorSort();
        Assert.assertEquals(allWithAuthorSort.size(), mapBooks.size());
    }

    @Test
    public void givenAuthorIncludingSymbol_whenCountSymbol_thenStatus200() {
        when(bookRepository.findAllIncludingSymbol('e')).thenReturn(listCharBooks);
        List<Map<String, String>> findAllIncludingSymbol = bookService.findAllIncludingSymbol('e');
        Assert.assertEquals(findAllIncludingSymbol.size(), mapBooks.size());
    }

    @Test
    public void saveBook_whenGetBook_thenStatus200() {
        when(bookRepository.save(book)).thenReturn(book);
        BookDto bookFromDb = bookService.save(book);
        Assert.assertEquals(bookFromDb.author(), book.author());
        Assert.assertEquals(bookFromDb.title(), book.title());
        Assert.assertEquals(bookFromDb.description(), book.description());
    }
}
