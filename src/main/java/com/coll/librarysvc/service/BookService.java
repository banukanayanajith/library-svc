package com.coll.librarysvc.service;

import com.coll.librarysvc.persistance.dto.BookDTO;
import com.coll.librarysvc.persistance.dto.BookRequestDTO;
import com.coll.librarysvc.persistance.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookService {

    Book registerBook(BookRequestDTO bookRequestDTO);

    Page<BookDTO> getAllBooks(Pageable pageable);

    Optional<Book> findById(Long id);
    Book save(Book book);
}