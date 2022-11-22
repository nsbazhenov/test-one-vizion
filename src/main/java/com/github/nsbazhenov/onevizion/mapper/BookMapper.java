package com.github.nsbazhenov.onevizion.mapper;

import com.github.nsbazhenov.onevizion.model.Book;
import com.github.nsbazhenov.onevizion.model.BookDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto bootToBookDto(Book book);
}
