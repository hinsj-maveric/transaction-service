package com.maveric.transactionservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maveric.transactionservice.feignclient.FeignAccountConsumer;
import com.maveric.transactionservice.feignclient.FeignBalanaceConsumer;
import com.maveric.transactionservice.repository.TransactionRepository;
import com.maveric.transactionservice.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.maveric.transactionservice.TransactionServiceApplicationTests.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {


    public static final String API_V1_ACCOUNTS ="/api/v1/accounts/1234/transactions";

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    FeignAccountConsumer feignAccountConsumer;

    @MockBean
    FeignBalanaceConsumer feignBalanaceConsumer;

    @Test
    void shouldThrowErrorWhenWrongDataPassedForCreateUser() throws Exception{
        mockMvc.perform(post(API_V1_ACCOUNTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getTransactionInvalidData()))
                        .header("userid", "1L")).andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void shouldThrowErrorWhenHeaderIdIsDifferentForGetAllTransactionByAccountId() throws Exception{
       mockMvc.perform(get(API_V1_ACCOUNTS + "?page=0&pageSize=2").header("userid", "1234"))
               .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldThrowErrorWhenHeaderIdIsDifferentForgetTransactionByAccountId() throws Exception{
       mockMvc.perform(get(API_V1_ACCOUNTS + "/1234").header("userid", "1234"))
               .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldThrowErrorWhenHeaderIdIsDifferentFordeleteTransactionByAccountId() throws Exception{
        mockMvc.perform(delete(API_V1_ACCOUNTS + "/1234").header("userid", "1234"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldThrowErrorWhenHeaderIdIsDifferentFordeleteAllTransactionsByAccountId() throws Exception{
        mockMvc.perform(delete(API_V1_ACCOUNTS + "/1234").header("userid", "1234"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}