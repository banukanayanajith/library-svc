package com.coll.librarysvc.persistance.dto;

import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private boolean borrowed;
    private Long borrowerId;
}