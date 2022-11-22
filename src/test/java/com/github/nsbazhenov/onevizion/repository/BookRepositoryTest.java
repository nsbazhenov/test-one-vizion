package com.github.nsbazhenov.onevizion.repository;

import com.github.nsbazhenov.onevizion.mapper.BookMapper;
import com.github.nsbazhenov.onevizion.model.Book;
import com.github.nsbazhenov.onevizion.model.BookDto;
import com.github.nsbazhenov.onevizion.service.KeyHolderFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookRepositoryTest {

    private static final long ID = 1;
    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final String DESCRIPTION = "description";
    private static final int COUNT = 1;

    private static Map<String, List<BookDto>> mapBooks;
    public static List<BookDto> bookList;
    public static BookDto book;
    private static List<Map<String, String>> listCharBooks;

    @InjectMocks
    private BookRepository bookRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private ResultSet resultSet;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private PreparedStatement stmt;

    @Mock
    private KeyHolderFactory keyHolderFactory;

    @Captor
    private ArgumentCaptor<RowMapper<Book>> rowMapperCaptor;

    @Captor
    private ArgumentCaptor<RowMapper<KeyHolder>> rowMapperKeyCaptor;

    @Captor
    private ArgumentCaptor<RowMapper<LinkedHashMap<String, String>>> rowLinkHashMapperCaptor;

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

    @Before
    public void prepareTest() throws SQLException {
        when(resultSet.getLong("id")).thenReturn(ID);
        when(resultSet.getString("title")).thenReturn(TITLE);
        when(resultSet.getString("author")).thenReturn(AUTHOR);
        when(resultSet.getString("description")).thenReturn(DESCRIPTION);
    }

    @Test
    public void givenBooksGroupedByAuthor_whenGetBooks_thenStatus200() throws Exception {
        bookRepository.findAllGroupedByAuthor();

        verify(jdbcTemplate).query(anyString(), rowMapperCaptor.capture());
        RowMapper<Book> rowMapper = rowMapperCaptor.getValue();
        Book employee = rowMapper.mapRow(resultSet, 1);
        Assert.assertEquals(ID, employee.id());
        Assert.assertEquals(TITLE, employee.title());
        Assert.assertEquals(AUTHOR, employee.author());
        Assert.assertEquals(DESCRIPTION, employee.description());
    }

    @Test
    public void givenBooksSortedByAuthor_whenGetBooks_thenStatus200() throws Exception {
        bookRepository.findAllWithAuthorSort();

        verify(jdbcTemplate).query(anyString(), rowMapperCaptor.capture());
        RowMapper<Book> rowMapper = rowMapperCaptor.getValue();
        Book employee = rowMapper.mapRow(resultSet, 1);
        Assert.assertEquals(ID, employee.id());
        Assert.assertEquals(TITLE, employee.title());
        Assert.assertEquals(AUTHOR, employee.author());
        Assert.assertEquals(DESCRIPTION, employee.description());
    }

    @Test
    public void givenAuthorIncludingSymbol_whenCountSymbol_thenStatus200() throws Exception {
        bookRepository.findAllIncludingSymbol('e');

        verify(jdbcTemplate).query(anyString(), rowLinkHashMapperCaptor.capture(), anyChar());
        RowMapper<LinkedHashMap<String, String>> rowMapper = rowLinkHashMapperCaptor.getValue();
        LinkedHashMap<String, String> linkedHashMap = rowMapper.mapRow(resultSet, 1);

        Assert.assertEquals(1, linkedHashMap.size());
    }

    @Test
    public void saveBook_whenGetBook_thenStatus200() {
        KeyHolder keyHolder = mock(GeneratedKeyHolder.class);

        when(keyHolderFactory.newKeyHolder()).thenReturn(keyHolder);
        when(keyHolder.getKey()).thenReturn(1L);
        when(jdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), Mockito.any(KeyHolder.class))).thenReturn(1);

        BookDto bookFromDb = bookRepository.save(book);

        Assert.assertEquals(1L, bookFromDb.id());
    }
}
