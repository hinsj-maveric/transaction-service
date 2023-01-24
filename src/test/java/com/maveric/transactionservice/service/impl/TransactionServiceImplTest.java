package com.maveric.transactionservice.service.impl;

import com.maveric.transactionservice.converter.DtoToModelConverter;
import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.exception.AccountIdMismatchException;
import com.maveric.transactionservice.exception.TransactionIdNotFoundException;
import com.maveric.transactionservice.model.Transaction;
import com.maveric.transactionservice.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static com.maveric.transactionservice.TransactionServiceApplicationTests.getTransaction;
import static com.maveric.transactionservice.TransactionServiceApplicationTests.getTransactionDto;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    DtoToModelConverter dtoToModelConverter;

    @InjectMocks
    TransactionServiceImpl transactionService;

    @Test
    void createTransaction() {
        when(dtoToModelConverter.dtoToModel(any(TransactionDto.class))).thenReturn(getTransaction());
        when(dtoToModelConverter.modelToDto(any(Transaction.class))).thenReturn(getTransactionDto());
        when(transactionRepository.save(any())).thenReturn(getTransaction());

        TransactionDto transactionDto = transactionService.createTransaction(getTransactionDto(), getTransactionDto().getAccountId());
        assertSame(transactionDto.getAccountId(), getTransaction().getAccountId());
    }

    @Test
    void getTransactionByAccountId() {
        Page<Transaction> page = new PageImpl<>(Arrays.asList(getTransaction(), getTransaction()));
        when(transactionRepository.findTransactionByAccountId(any(Pageable.class), any())).thenReturn(page);
        when(dtoToModelConverter.modelToDto(any(Transaction.class))).thenReturn(getTransactionDto());

        List<TransactionDto> transactionDtos = transactionService.getTransactionByAccountId(0,
                2,
                "maveric-1");

        assertNotNull(transactionDtos);
        assertEquals("maveric-1", transactionDtos.get(0).getAccountId());
    }

    @Test
    void getTransactionIdByAccountId() throws AccountIdMismatchException, TransactionIdNotFoundException {
        when(transactionRepository.findById(anyString())).thenReturn(Optional.of(getTransaction()));
        when(dtoToModelConverter.modelToDto(any(Transaction.class))).thenReturn(getTransactionDto());

        TransactionDto transactionDto = transactionService.getTransactionIdByAccountId("maveric-1", "1234");
        assertNotNull(transactionDto);
        assertSame(transactionDto.getType(), getTransaction().getType());
    }

    @Test
    void deleteTransactionIdByAccountId() throws AccountIdMismatchException, TransactionIdNotFoundException {
        when(transactionRepository.findById(anyString())).thenReturn(Optional.of(getTransaction()));
        willDoNothing().given(transactionRepository).deleteById(anyString());

        transactionService.deleteTransactionIdByAccountId("maveric-1", "1234");

        verify(transactionRepository).deleteById("1234");
    }
}