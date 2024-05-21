package com.coll.librarysvc.web.util;


import com.coll.librarysvc.persistance.dto.BookDTO;
import com.coll.librarysvc.persistance.dto.BookRequestDTO;
import com.coll.librarysvc.persistance.dto.BorrowerDTO;
import com.coll.librarysvc.persistance.dto.BorrowerRequestDTO;
import com.coll.librarysvc.persistance.entity.Book;
import com.coll.librarysvc.persistance.entity.Borrower;

public class Mapper {
    public static BookDTO mapToBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setBorrowed(book.isBorrowed());
        bookDTO.setBorrowerId(book.getBorrowerId());
        return bookDTO;
    }

    public static Book mapToBook(BookRequestDTO bookRequestDTO) {
        Book book = new Book();
        book.setIsbn(bookRequestDTO.getIsbn());
        book.setTitle(bookRequestDTO.getTitle());
        book.setAuthor(bookRequestDTO.getAuthor());
        return book;
    }

    public static BorrowerDTO mapToBorrowerDTO(Borrower borrower) {
        BorrowerDTO borrowerDTO = new BorrowerDTO();
        borrowerDTO.setId(borrower.getId());
        borrowerDTO.setName(borrower.getName());
        borrowerDTO.setEmail(borrower.getEmail());
        return borrowerDTO;
    }

    public static Borrower mapToBorrower(BorrowerRequestDTO borrowerRequestDTO) {
        Borrower borrower = new Borrower();
        borrower.setName(borrowerRequestDTO.getName());
        borrower.setEmail(borrowerRequestDTO.getEmail());
        return borrower;
    }
}
