package com.maveric.transactionservice.dto;

import com.maveric.transactionservice.constant.Currency;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class BalanceDto {
    private String  _id;

    private String accountId;

    private Number amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;
}
