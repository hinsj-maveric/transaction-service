package com.maveric.transactionservice.service;

import com.maveric.transactionservice.dto.TransactionDto;

public interface TransactionService {

    TransactionDto createTransaction (TransactionDto transactionDto, String accountId);
}
