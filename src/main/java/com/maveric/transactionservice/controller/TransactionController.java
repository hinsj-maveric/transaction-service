package com.maveric.transactionservice.controller;

import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.exception.AccountIdMismatchException;
import com.maveric.transactionservice.exception.TransactionIdNotFoundException;
import com.maveric.transactionservice.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("accounts/{accountId}/transactions")
    public ResponseEntity<TransactionDto> createUser(@PathVariable("accountId") String accountId,
                                                     @Valid @RequestBody TransactionDto transactionDto) {
        return new ResponseEntity<>(transactionService.createTransaction(transactionDto, accountId), HttpStatus.CREATED);
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public List<TransactionDto> getAllTransactionByAccountId(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                             @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
                                                             @PathVariable("accountId") String accountId) {
        return transactionService.getTransactionByAccountId(page, pageSize, accountId);
    }

    @GetMapping("/accounts/{accountId}/transactions/{transactionId}")
    public ResponseEntity<TransactionDto> getTransactionByAccountId(@PathVariable("accountId") String accountId,
                                                                    @PathVariable("transactionId") String transactionId)
                                                                    throws TransactionIdNotFoundException, AccountIdMismatchException {
        return new ResponseEntity<>(transactionService.getTransactionIdByAccountId(accountId, transactionId), HttpStatus.OK);
    }

}
