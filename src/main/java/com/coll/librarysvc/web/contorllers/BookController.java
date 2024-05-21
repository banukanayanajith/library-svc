package com.coll.librarysvc.web.contorllers;

import com.coll.librarysvc.persistance.dto.BookDTO;
import com.coll.librarysvc.persistance.dto.BookRequestDTO;
import com.coll.librarysvc.service.BookService;
import com.coll.librarysvc.web.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDTO> registerBook(@Validated @RequestBody BookRequestDTO bookRequestDTO) {
        BookDTO bookDTO = Mapper.mapToBookDTO(bookService.registerBook(bookRequestDTO));
        return ResponseEntity.ok(bookDTO);
    }

    @GetMapping
    public ResponseEntity<Page<BookDTO>> getAllBooks(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookDTO> booksPage = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(booksPage);
    }
}
