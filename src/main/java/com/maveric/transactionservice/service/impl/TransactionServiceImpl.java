package com.maveric.transactionservice.service.impl;

import com.maveric.transactionservice.converter.DtoToModelConverter;
import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.exception.AccountIdMismatchException;
import com.maveric.transactionservice.model.Transaction;
import com.maveric.transactionservice.repository.TransactionRepository;
import com.maveric.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    DtoToModelConverter dtoToModelConverter;

    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto, String accountId) {
        if(accountId.equals(transactionDto.getAccountId())){
            Transaction transaction = dtoToModelConverter.dtoToModel(transactionDto);
            return dtoToModelConverter.modelToDto(transactionRepository.save(transaction));
        } else {
            try {
                throw new AccountIdMismatchException("The account ID " + accountId + " mismatched with the provided data");
            } catch (AccountIdMismatchException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
