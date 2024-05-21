package com.coll.librarysvc.service;

import com.coll.librarysvc.exception.BookNotFoundException;
import com.coll.librarysvc.exception.BorrowerNotFoundException;
import com.coll.librarysvc.exception.FailedBorrowException;
import com.coll.librarysvc.exception.FailedReturnException;
import com.coll.librarysvc.persistance.dto.BookDTO;
import com.coll.librarysvc.persistance.entity.Book;
import com.coll.librarysvc.persistance.entity.Borrower;
import com.coll.librarysvc.web.util.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LibrarySystemServiceImplTest {

    @Mock
    private BookService mockBookService;
    @Mock
    private BorrowerService mockBorrowerService;

    private LibrarySystemServiceImpl librarySystemServiceImplUnderTest;

    private static final String BORROWER_NAME = "Banuka Nayanajith";
    private static final String BORROWER_EMAIL = "banuka@gmail.com";
    private final String VALID_ISBN = "1234567890";
    private final String VALID_TITLE = "Test Book";
    private final String VALID_AUTHOR = "Test Author";

    @BeforeEach
    void setUp() {
        librarySystemServiceImplUnderTest = new LibrarySystemServiceImpl(mockBookService, mockBorrowerService);
    }

    @Test
    void testBorrowBook() {

        final Borrower borrower = new Borrower(1L, BORROWER_NAME, BORROWER_EMAIL);
        final Book book = new Book(1L, VALID_ISBN, VALID_TITLE, VALID_AUTHOR, false, null);

        final Optional<Borrower> borrowerOptional = Optional.of(borrower);
        final Optional<Book> bookOptional = Optional.of(book);
        final Book savedBook = new Book(1L, VALID_ISBN, VALID_TITLE, VALID_AUTHOR, true, 1L);
        final Optional<BookDTO> expectedResult = Optional.of(Mapper.mapToBookDTO(savedBook));

        when(mockBorrowerService.findById(1L)).thenReturn(borrowerOptional);
        when(mockBookService.findById(1L)).thenReturn(bookOptional);
        when(mockBookService.save(any(Book.class))).thenReturn(savedBook);

        final Optional<BookDTO> result = librarySystemServiceImplUnderTest.borrowBook(1L, 1L);


        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testBorrowBook_BorrowerServiceReturnsAbsent() {

        when(mockBorrowerService.findById(1L)).thenReturn(Optional.empty());


        assertThatThrownBy(() -> librarySystemServiceImplUnderTest.borrowBook(1L, 1L))
                .isInstanceOf(BorrowerNotFoundException.class);
    }

    @Test
    void testBorrowBook_BookServiceFindByIdReturnsAbsent() {

        final Optional<Borrower> borrowerOptional = Optional.of(new Borrower(1L, BORROWER_NAME, BORROWER_EMAIL));
        when(mockBorrowerService.findById(1L)).thenReturn(borrowerOptional);
        when(mockBookService.findById(1L)).thenReturn(Optional.empty());


        assertThatThrownBy(() -> librarySystemServiceImplUnderTest.borrowBook(1L, 1L))
                .isInstanceOf(FailedBorrowException.class);
    }

    @Test
    void testReturnBook() {

        final Book book = new Book(1L, VALID_ISBN, VALID_TITLE, VALID_AUTHOR, true, 1L);
        final Optional<Book> bookOptional = Optional.of(book);
        final Book returnedBook = new Book(1L, VALID_ISBN, VALID_TITLE, VALID_AUTHOR, false, null);
        final Optional<BookDTO> expectedResult = Optional.of(Mapper.mapToBookDTO(returnedBook));

        when(mockBookService.findById(1L)).thenReturn(bookOptional);
        when(mockBookService.save(any(Book.class))).thenReturn(returnedBook);

        final Optional<BookDTO> result = librarySystemServiceImplUnderTest.returnBook(1L);


        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testReturnBook_BookServiceFindByIdReturnsAbsent() {

        when(mockBookService.findById(1L)).thenReturn(Optional.empty());


        assertThatThrownBy(() -> librarySystemServiceImplUnderTest.returnBook(1L))
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void testBorrowBook_BookAlreadyBorrowed() {

        final Borrower borrower = new Borrower(1L, BORROWER_NAME, BORROWER_EMAIL);
        final Book book = new Book(1L, VALID_ISBN, VALID_TITLE, VALID_AUTHOR, true, 2L);

        final Optional<Borrower> borrowerOptional = Optional.of(borrower);
        final Optional<Book> bookOptional = Optional.of(book);

        when(mockBorrowerService.findById(1L)).thenReturn(borrowerOptional);
        when(mockBookService.findById(1L)).thenReturn(bookOptional);


        assertThatThrownBy(() -> librarySystemServiceImplUnderTest.borrowBook(1L, 1L))
                .isInstanceOf(FailedBorrowException.class);
    }

    @Test
    void testBorrowBook_BorrowerAttemptsToBorrowSameBook() {

        final Borrower borrower = new Borrower(1L, BORROWER_NAME, BORROWER_EMAIL);
        final Book book = new Book(1L, VALID_ISBN, VALID_TITLE, VALID_AUTHOR, true, 1L);

        final Optional<Borrower> borrowerOptional = Optional.of(borrower);
        final Optional<Book> bookOptional = Optional.of(book);

        when(mockBorrowerService.findById(1L)).thenReturn(borrowerOptional);
        when(mockBookService.findById(1L)).thenReturn(bookOptional);


        assertThatThrownBy(() -> librarySystemServiceImplUnderTest.borrowBook(1L, 1L))
                .isInstanceOf(FailedBorrowException.class);
    }

    @Test
    void testReturnBook_BookNotBorrowed() {

        final Book book = new Book(1L, VALID_ISBN, VALID_TITLE, VALID_AUTHOR, false, null);
        final Optional<Book> bookOptional = Optional.of(book);

        when(mockBookService.findById(1L)).thenReturn(bookOptional);


        assertThatThrownBy(() -> librarySystemServiceImplUnderTest.returnBook(1L))
                .isInstanceOf(FailedReturnException.class);
    }

}