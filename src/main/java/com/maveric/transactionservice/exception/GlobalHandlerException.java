package com.maveric.transactionservice.exception;

import com.maveric.transactionservice.constant.MessageConstant;
import com.maveric.transactionservice.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(AccountIdMismatchException.class)
    public ResponseEntity<ErrorDto> handleAccountIdMismatchException(AccountIdMismatchException e) {
        ErrorDto error = getError(e.getMessage(), String.valueOf(HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
        ErrorDto error = getError(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                String.valueOf(HttpStatus.BAD_REQUEST));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> handleFormatException(HttpMessageNotReadableException e) {
        ErrorDto error = getError(MessageConstant.TYPE_ERROR, String.valueOf(HttpStatus.BAD_REQUEST));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private ErrorDto getError(String message , String code){
        ErrorDto error = new ErrorDto();
        error.setCode(code);
        error.setMessage(message);
        return error;
    }
}
