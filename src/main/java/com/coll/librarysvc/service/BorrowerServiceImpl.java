package com.coll.librarysvc.service;

import com.coll.librarysvc.persistance.dto.BorrowerRequestDTO;
import com.coll.librarysvc.persistance.entity.Borrower;
import com.coll.librarysvc.persistance.repository.BorrowerRepository;
import com.coll.librarysvc.web.util.Mapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BorrowerServiceImpl implements BorrowerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BorrowerServiceImpl.class);

    private final BorrowerRepository borrowerRepository;

    @Override
    public Borrower registerBorrower(BorrowerRequestDTO borrowerRequestDTO) {
        LOGGER.info("Registering new borrower with details: {}", borrowerRequestDTO);
        Borrower borrower = Mapper.mapToBorrower(borrowerRequestDTO);
        Borrower savedBorrower = borrowerRepository.save(borrower);
        LOGGER.info("Borrower registered successfully with ID: {}", savedBorrower.getId());
        return savedBorrower;
    }

    @Override
    public Optional<Borrower> findById(Long id) {
        LOGGER.info("Searching for borrower with ID: {}", id);
        Optional<Borrower> borrower = borrowerRepository.findById(id);
        if (borrower.isPresent()) {
            LOGGER.info("Borrower found with ID: {}", id);
        } else {
            LOGGER.warn("Borrower not found with ID: {}", id);
        }
        return borrower;
    }
}