package com.maveric.transactionservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.repository.TransactionRepository;
import com.maveric.transactionservice.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.maveric.transactionservice.TransactionServiceApplicationTests.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {


    public static final String API_V1_ACCOUNTS ="/api/v1/accounts/maveric-1/transactions";

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser() throws Exception{
        when(transactionService.createTransaction(any(TransactionDto.class), anyString())).thenReturn(getTransactionDto());
        mockMvc.perform(post(API_V1_ACCOUNTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getTransaction()))).andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldThrowErrorWhenWrongDataPassedForCreateUser() throws Exception{
        when(transactionService.createTransaction(any(TransactionDto.class), anyString())).thenReturn(getTransactionDto());
        mockMvc.perform(post(API_V1_ACCOUNTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getTransactionInvalidData()))).andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void getAllTransactionByAccountId() throws Exception{
        when(transactionService.getTransactionByAccountId(anyInt(), anyInt(), anyString())).thenReturn(Arrays.asList(getTransactionDto(), getTransactionDto()));
        mockMvc.perform(get(API_V1_ACCOUNTS + "?page=0&pageSize=2"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getTransactionByAccountId() throws Exception{
        when(transactionService.getTransactionIdByAccountId(anyString(), anyString())).thenReturn(getTransactionDto());
        mockMvc.perform(get(API_V1_ACCOUNTS + "/1234"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteTransactionByAccountId() throws Exception{
        mockMvc.perform(delete(API_V1_ACCOUNTS + "/1234"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}