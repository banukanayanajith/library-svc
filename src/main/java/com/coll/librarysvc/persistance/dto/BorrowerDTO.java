package com.coll.librarysvc.persistance.dto;

import lombok.Data;

@Data
public class BorrowerDTO {
    private Long id;
    private String name;
    private String email;
}