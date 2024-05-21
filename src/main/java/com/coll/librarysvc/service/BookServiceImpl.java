package com.coll.librarysvc.service;

import com.coll.librarysvc.exception.InvalidBookException;
import com.coll.librarysvc.persistance.dto.BookDTO;
import com.coll.librarysvc.persistance.dto.BookRequestDTO;
import com.coll.librarysvc.persistance.entity.Book;
import com.coll.librarysvc.persistance.repository.BookRepository;
import com.coll.librarysvc.web.util.Mapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    @Override
    public Book registerBook(BookRequestDTO bookRequestDTO) {
        LOGGER.info("Registering book with ISBN: {}", bookRequestDTO.getIsbn());

        List<Book> existingBooks = bookRepository.findByIsbn(bookRequestDTO.getIsbn());

        for (Book book : existingBooks) {
            if (!book.getTitle().equals(bookRequestDTO.getTitle()) || !book.getAuthor().equals(bookRequestDTO.getAuthor())) {
                LOGGER.error("Invalid book registration attempt: ISBN {} already exists with different title/author", bookRequestDTO.getIsbn());
                throw new InvalidBookException("Book with same ISBN must have same title and author");
            }
        }

        Book savedBook = bookRepository.save(Mapper.mapToBook(bookRequestDTO));
        LOGGER.info("Book registered successfully with ID: {}", savedBook.getId());
        return savedBook;
    }

    @Override
    public Page<BookDTO> getAllBooks(Pageable pageable) {
        LOGGER.info("Fetching all books with pageable: {}", pageable);

        Page<Book> booksPage = bookRepository.findAll(pageable);
        Page<BookDTO> bookDTOPage = booksPage.map(book -> {
            BookDTO dto = new BookDTO();
            dto.setId(book.getId());
            dto.setIsbn(book.getIsbn());
            dto.setTitle(book.getTitle());
            dto.setAuthor(book.getAuthor());
            return dto;
        });

        LOGGER.info("Fetched {} books", bookDTOPage.getTotalElements());
        return bookDTOPage;
    }

    @Override
    public Optional<Book> findById(Long id) {
        LOGGER.info("Searching for book with ID: {}", id);
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            LOGGER.info("Book found with ID: {}", id);
        } else {
            LOGGER.warn("Book not found with ID: {}", id);
        }
        return book;
    }

    @Override
    public Book save(Book book) {
        LOGGER.info("Saving book with ID: {}", book.getId());
        Book savedBook = bookRepository.save(book);
        LOGGER.info("Book saved successfully with ID: {}", savedBook.getId());
        return savedBook;
    }
}