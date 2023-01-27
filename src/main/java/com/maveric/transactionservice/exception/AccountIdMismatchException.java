package com.maveric.transactionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AccountIdMismatchException extends RuntimeException{
    public AccountIdMismatchException(String message) {
        super(message);
    }
}
