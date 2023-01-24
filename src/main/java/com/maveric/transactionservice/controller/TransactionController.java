package com.maveric.transactionservice.controller;

import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("/accounts/{accountId}/transactions")
    public List<TransactionDto> getAllTransactionByAccountId(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                             @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
                                                             @PathVariable("accountId") String accountId) {
        return transactionService.getTransactionByAccountId(page, pageSize, accountId);
    }

}
