package com.maveric.transactionservice.service;

import com.maveric.transactionservice.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> getTransactionByAccountId(int page, int pageSize, String accountId);
}
