package com.coll.librarysvc.service;

import com.coll.librarysvc.persistance.dto.BookDTO;

import java.util.Optional;

public interface LibrarySystemService {
    Optional<BookDTO> borrowBook(Long borrowerId, Long bookId);
    Optional<BookDTO> returnBook(Long bookId);
}