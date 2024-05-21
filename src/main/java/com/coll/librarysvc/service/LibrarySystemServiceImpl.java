package com.coll.librarysvc.service;

import com.coll.librarysvc.exception.BookNotFoundException;
import com.coll.librarysvc.exception.BorrowerNotFoundException;
import com.coll.librarysvc.exception.FailedBorrowException;
import com.coll.librarysvc.exception.FailedReturnException;
import com.coll.librarysvc.persistance.dto.BookDTO;
import com.coll.librarysvc.persistance.entity.Book;
import com.coll.librarysvc.persistance.entity.Borrower;
import com.coll.librarysvc.web.util.Mapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibrarySystemServiceImpl implements LibrarySystemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LibrarySystemServiceImpl.class);

    private final BookService bookService;
    private final BorrowerService borrowerService;

    @Override
    @Transactional
    public Optional<BookDTO> borrowBook(Long borrowerId, Long bookId) {
        LOGGER.info("Attempting to borrow book with ID {} for borrower with ID {}", bookId, borrowerId);

        Borrower borrower = borrowerService.findById(borrowerId)
                .orElseThrow(() -> {
                    LOGGER.error("Borrower not found with ID {}", borrowerId);
                    return new BorrowerNotFoundException("Borrower not found with id: " + borrowerId);
                });

        Book book = bookService.findById(bookId)
                .orElseThrow(() -> {
                    LOGGER.error("Book not found with ID {}", bookId);
                    return new FailedBorrowException("Book not found with id: " + bookId);
                });

        if (!book.isBorrowed()) {
            book.setBorrowed(true);
            book.setBorrowerId(borrower.getId());
            Book updatedBook = bookService.save(book);
            LOGGER.info("Book with ID {} successfully borrowed by borrower with ID {}", bookId, borrowerId);
            return Optional.of(Mapper.mapToBookDTO(updatedBook));
        }

        LOGGER.error("Failed to borrow book with ID {}: already borrowed", bookId);
        throw new FailedBorrowException("Book is already borrowed with id: " + bookId);
    }

    @Override
    @Transactional
    public Optional<BookDTO> returnBook(Long bookId) {
        LOGGER.info("Attempting to return book with ID {}", bookId);

        Book book = bookService.findById(bookId)
                .orElseThrow(() -> {
                    LOGGER.error("Book not found with ID {}", bookId);
                    return new BookNotFoundException("Book not found with id: " + bookId);
                });

        if (book.isBorrowed()) {
            book.setBorrowed(false);
            book.setBorrowerId(null);
            Book updatedBook = bookService.save(book);
            LOGGER.info("Book with ID {} successfully returned", bookId);
            return Optional.of(Mapper.mapToBookDTO(updatedBook));
        }

        LOGGER.error("Failed to return book with ID {}: not currently borrowed", bookId);
        throw new FailedReturnException("Book is not borrowed with id: " + bookId);
    }
}