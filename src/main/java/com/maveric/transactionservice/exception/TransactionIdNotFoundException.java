package com.maveric.transactionservice.exception;

public class TransactionIdNotFoundException extends RuntimeException{
    public TransactionIdNotFoundException(String message) {
        super(message);
    }
}
