package com.coll.librarysvc.service;

import com.coll.librarysvc.persistance.dto.BorrowerRequestDTO;
import com.coll.librarysvc.persistance.entity.Borrower;

import java.util.Optional;

public interface BorrowerService {

    Borrower registerBorrower(BorrowerRequestDTO borrowerRequestDTO);
    Optional<Borrower> findById(Long id);
}