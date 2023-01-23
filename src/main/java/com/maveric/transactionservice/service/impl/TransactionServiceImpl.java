package com.maveric.transactionservice.service.impl;

import com.maveric.transactionservice.converter.DtoToModelConverter;
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
}
