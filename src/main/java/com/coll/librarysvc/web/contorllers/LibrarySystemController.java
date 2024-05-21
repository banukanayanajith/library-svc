package com.coll.librarysvc.web.contorllers;

import com.coll.librarysvc.persistance.dto.BookDTO;
import com.coll.librarysvc.service.LibrarySystemService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/library")
@RequiredArgsConstructor
public class LibrarySystemController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LibrarySystemController.class);

    private final LibrarySystemService librarySystemService;

    @PostMapping("/borrower/{borrowerId}/borrow/{bookId}")
    public ResponseEntity<BookDTO> borrowBook(@PathVariable Long borrowerId, @PathVariable Long bookId) {
        LOGGER.info("Received request to borrow book with ID {} for borrower with ID {}", bookId, borrowerId);
        return librarySystemService.borrowBook(borrowerId, bookId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/borrower/return/{bookId}")
    public ResponseEntity<BookDTO> returnBook(@PathVariable Long bookId) {
        LOGGER.info("Received request to return book with ID {}", bookId);

        return librarySystemService.returnBook(bookId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
}
