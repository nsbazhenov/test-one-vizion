package com.github.nsbazhenov.onevizion.repository;

import com.github.nsbazhenov.onevizion.mapper.BookMapper;
import com.github.nsbazhenov.onevizion.model.Book;
import com.github.nsbazhenov.onevizion.model.BookDto;
import com.github.nsbazhenov.onevizion.service.GeneratedKeyHolderFactory;
import com.github.nsbazhenov.onevizion.service.KeyHolderFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  Repository to retrieve data from the database.
 *
 * @author Bazhenov Nikita
 *
 */
@Repository
public class BookRepository {

    private static final String GET_ALL_BOOKS = "SELECT * " +
            "FROM BOOK " +
            "ORDER BY TITLE DESC;";
    private static final String SAVE_BOOK = "INSERT INTO BOOK (TITLE, AUTHOR, DESCRIPTION) " +
            "VALUES ($1, $2, $3);";
    private static final String SEARCH_SYMBOLS = "SELECT AUTHOR, " +
            "SUM((CHAR_LENGTH(TITLE) - CHAR_LENGTH(REPLACE(LOWER(TITLE), LOWER($1), ''))) / CHAR_LENGTH($1)) AS COUNT " +
            "FROM BOOK " +
            "GROUP BY AUTHOR " +
            "HAVING COUNT > 0 " +
            "ORDER BY COUNT DESC " +
            "LIMIT 10";

    private final JdbcTemplate jdbcTemplate;
    private final BookMapper mapper;
    private final KeyHolderFactory keyHolderFactory;

    public BookRepository(JdbcTemplate jdbcTemplate, BookMapper mapper,
                          KeyHolderFactory keyHolderFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
        this.keyHolderFactory = keyHolderFactory;
    }

    public BookDto save(BookDto book) {
        KeyHolder keyHolder = keyHolderFactory.newKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SAVE_BOOK,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.title());
            ps.setString(2, book.author());
            ps.setString(3, book.description());
            return ps;
        }, keyHolder);

        return new BookDto((long) keyHolder.getKey(), book.title(), book.author(), book.description());
    }

    public List<BookDto> findAllWithAuthorSort() {
        List<Book> books = jdbcTemplate.query(GET_ALL_BOOKS, (resultSet
                , rowNum) ->
                new Book(resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("description")));

        return books.stream()
                .map(mapper::bootToBookDto)
                .collect(Collectors.toList());
    }

    public Map<String, List<BookDto>> findAllGroupedByAuthor() {
        return findAllWithAuthorSort().stream()
                .collect(Collectors.groupingBy(BookDto::author));
    }

    public List<Map<String, String>> findAllIncludingSymbol(char symbol) {
        return jdbcTemplate.query(SEARCH_SYMBOLS, (resultSet, rowNum) -> {
            LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
            linkedHashMap.put(resultSet.getString("author"),
                    resultSet.getString("count"));

            return linkedHashMap;
        }, symbol);
    }
}
