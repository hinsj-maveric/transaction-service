package com.maveric.transactionservice.controller;

import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("accounts/{accountId}/transactions")
    public ResponseEntity<TransactionDto> createUser(@PathVariable("accountId") String accountId,
                                                     @Valid @RequestBody TransactionDto transactionDto){
        return new ResponseEntity<>(transactionService.createTransaction(transactionDto, accountId), HttpStatus.CREATED);
    }

}
