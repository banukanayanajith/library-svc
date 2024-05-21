package com.coll.librarysvc.web.contorllers;

import com.coll.librarysvc.persistance.dto.BorrowerDTO;
import com.coll.librarysvc.persistance.dto.BorrowerRequestDTO;
import com.coll.librarysvc.service.BorrowerService;
import com.coll.librarysvc.web.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/borrower")
@RequiredArgsConstructor
public class BorrowerController {
    private final BorrowerService borrowerService;

    @PostMapping
    public ResponseEntity<BorrowerDTO> registerBorrower(@Validated @RequestBody BorrowerRequestDTO borrowerRequestDTO) {
        BorrowerDTO borrowerDTO = Mapper.mapToBorrowerDTO(borrowerService.registerBorrower(borrowerRequestDTO));
        return ResponseEntity.ok(borrowerDTO);
    }
}