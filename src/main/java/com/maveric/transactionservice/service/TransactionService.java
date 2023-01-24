package com.maveric.transactionservice.service;

import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.exception.AccountIdMismatchException;
import com.maveric.transactionservice.exception.TransactionIdNotFoundException;

public interface TransactionService {
    void deleteTransactionIdByAccountId(String accountId, String transactionId) throws TransactionIdNotFoundException, AccountIdMismatchException;
}
