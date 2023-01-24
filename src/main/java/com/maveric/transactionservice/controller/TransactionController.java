package com.maveric.transactionservice.controller;

import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.exception.AccountIdMismatchException;
import com.maveric.transactionservice.exception.TransactionIdNotFoundException;
import com.maveric.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("/accounts/{accountId}/transactions/{transactionId}")
    public ResponseEntity<TransactionDto> getTransactionByAccountId(@PathVariable("accountId") String accountId,
                                                                    @PathVariable("transactionId") String transactionId)
                                                                    throws TransactionIdNotFoundException, AccountIdMismatchException {
        return new ResponseEntity<>(transactionService.getTransactionIdByAccountId(accountId, transactionId), HttpStatus.OK);
    }

}
