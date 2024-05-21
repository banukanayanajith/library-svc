package com.coll.librarysvc.service;

import com.coll.librarysvc.persistance.dto.BorrowerRequestDTO;
import com.coll.librarysvc.persistance.entity.Borrower;
import com.coll.librarysvc.persistance.repository.BorrowerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BorrowerServiceImplTest {

    @Mock
    private BorrowerRepository mockBorrowerRepository;

    private BorrowerServiceImpl borrowerServiceImplUnderTest;

    private static final String BORROWER_NAME = "Banuka Nayanajith";
    private static final String BORROWER_EMAIL = "banuka@gmail.com";

    @BeforeEach
    void setUp() {
        borrowerServiceImplUnderTest = new BorrowerServiceImpl(mockBorrowerRepository);
    }

    @Test
    void testRegisterBorrower() {
        BorrowerRequestDTO borrowerRequestDTO = new BorrowerRequestDTO();
        borrowerRequestDTO.setName(BORROWER_NAME);
        borrowerRequestDTO.setEmail(BORROWER_EMAIL);

        Borrower expectedBorrower = new Borrower(1L, BORROWER_NAME, BORROWER_EMAIL);

        when(mockBorrowerRepository.save(any(Borrower.class))).thenReturn(expectedBorrower);

        Borrower result = borrowerServiceImplUnderTest.registerBorrower(borrowerRequestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo(BORROWER_NAME);
        assertThat(result.getEmail()).isEqualTo(BORROWER_EMAIL);
    }

    @Test
    void testFindById() {
        Borrower expectedBorrower = new Borrower(1L, BORROWER_NAME, BORROWER_EMAIL);
        Optional<Borrower> borrower = Optional.of(expectedBorrower);

        when(mockBorrowerRepository.findById(1L)).thenReturn(borrower);

        Optional<Borrower> result = borrowerServiceImplUnderTest.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedBorrower);
    }

    @Test
    void testFindById_BorrowerRepositoryReturnsAbsent() {
        when(mockBorrowerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Borrower> result = borrowerServiceImplUnderTest.findById(1L);

        assertThat(result).isEmpty();
    }
}