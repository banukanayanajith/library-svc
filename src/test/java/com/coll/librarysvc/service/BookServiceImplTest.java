package com.coll.librarysvc.service;

import com.coll.librarysvc.exception.InvalidBookException;
import com.coll.librarysvc.persistance.dto.BookDTO;
import com.coll.librarysvc.persistance.dto.BookRequestDTO;
import com.coll.librarysvc.persistance.entity.Book;
import com.coll.librarysvc.persistance.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository mockBookRepository;

    private BookServiceImpl bookServiceImplUnderTest;

    private final String VALID_ISBN = "1234567890";
    private final String VALID_TITLE = "Test Book";
    private final String VALID_AUTHOR = "Test Author";

    @BeforeEach
    void setUp() {
        bookServiceImplUnderTest = new BookServiceImpl(mockBookRepository);
    }

    @Test
    void testRegisterBook() {
        BookRequestDTO bookRequestDTO = new BookRequestDTO(VALID_ISBN, VALID_TITLE, VALID_AUTHOR);

        List<Book> existingBooks = Collections.emptyList();
        when(mockBookRepository.findByIsbn(VALID_ISBN)).thenReturn(existingBooks);

        Book savedBook = new Book(VALID_ISBN, VALID_TITLE, VALID_AUTHOR, false);
        when(mockBookRepository.save(any(Book.class))).thenReturn(savedBook);

        final Book result = bookServiceImplUnderTest.registerBook(bookRequestDTO);

        assertThat(result.getIsbn()).isEqualTo(VALID_ISBN);
        assertThat(result.getTitle()).isEqualTo(VALID_TITLE);
        assertThat(result.getAuthor()).isEqualTo(VALID_AUTHOR);
    }

    @Test
    void testRegisterBook_BookAlreadyExistsWithDifferentDetails() {
        BookRequestDTO bookRequestDTO = new BookRequestDTO(VALID_ISBN, VALID_TITLE, VALID_AUTHOR);

        Book existingBook = new Book(VALID_ISBN, "Test Title 2", "Test Author 2", false);
        List<Book> existingBooks = Collections.singletonList(existingBook);
        when(mockBookRepository.findByIsbn(VALID_ISBN)).thenReturn(existingBooks);

        assertThrows(InvalidBookException.class, () -> bookServiceImplUnderTest.registerBook(bookRequestDTO));
    }

    @Test
    void testGetAllBooks() {
        Book book = new Book(VALID_ISBN, VALID_TITLE, VALID_AUTHOR, false);
        List<Book> books = Collections.singletonList(book);
        Page<Book> pagedBooks = new PageImpl<>(books);
        when(mockBookRepository.findAll(any(Pageable.class))).thenReturn(pagedBooks);

        final Page<BookDTO> result = bookServiceImplUnderTest.getAllBooks(PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void testFindById_BookExists() {
        Book expectedBook = new Book(VALID_ISBN, VALID_TITLE, VALID_AUTHOR, false);
        when(mockBookRepository.findById(1L)).thenReturn(Optional.of(expectedBook));

        Optional<Book> actualBook = bookServiceImplUnderTest.findById(1L);

        assertThat(actualBook).isPresent();
        assertThat(actualBook).contains(expectedBook);
    }

    @Test
    void testFindById_BookNotFound() {
        Long nonExistingId = 10L;
        when(mockBookRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        Optional<Book> actualBook = bookServiceImplUnderTest.findById(nonExistingId);

        assertThat(actualBook).isEmpty();
    }

    @Test
    void testSave_NewBook() {
        Book newBook = new Book(VALID_ISBN, VALID_TITLE, VALID_AUTHOR, false);
        when(mockBookRepository.save(newBook)).thenReturn(newBook);

        Book savedBook = bookServiceImplUnderTest.save(newBook);

        assertThat(savedBook).isEqualTo(newBook);
        verify(mockBookRepository, times(1)).save(newBook);
    }
}
