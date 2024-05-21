package com.coll.librarysvc.persistance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Pattern(regexp = "\\d{13}")
    private String isbn;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    private boolean borrowed = false;

    private Long borrowerId;

    public Book(String isbn, String title, String author, boolean borrowed) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.borrowed = borrowed;
    }
}
