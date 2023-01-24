package com.maveric.transactionservice.service.impl;

import com.maveric.transactionservice.converter.DtoToModelConverter;
import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.exception.AccountIdMismatchException;
import com.maveric.transactionservice.exception.TransactionIdNotFoundException;
import com.maveric.transactionservice.model.Transaction;
import com.maveric.transactionservice.repository.TransactionRepository;
import com.maveric.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    DtoToModelConverter dtoToModelConverter;

    @Override
    public TransactionDto getTransactionIdByAccountId(String accountId, String transactionId) throws TransactionIdNotFoundException, AccountIdMismatchException {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(
                () -> new TransactionIdNotFoundException("Transaction id not available")
        );
        if(accountId.equals(transaction.getAccountId())) {
            return dtoToModelConverter.modelToDto(transaction);
        } else {
            throw new AccountIdMismatchException("Account Id not available");
        }
    }
}
