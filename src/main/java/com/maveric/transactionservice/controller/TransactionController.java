package com.maveric.transactionservice.controller;

import com.maveric.transactionservice.constant.Type;
import com.maveric.transactionservice.dto.AccountDto;
import com.maveric.transactionservice.dto.BalanceDto;
import com.maveric.transactionservice.dto.TransactionDto;
import com.maveric.transactionservice.exception.AccountIdMismatchException;
import com.maveric.transactionservice.exception.TransactionAmountException;
import com.maveric.transactionservice.exception.TransactionIdNotFoundException;
import com.maveric.transactionservice.feignclient.FeignAccountConsumer;
import com.maveric.transactionservice.feignclient.FeignBalanaceConsumer;
import com.maveric.transactionservice.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @Autowired
    FeignBalanaceConsumer feignBalanaceConsumer;

    @Autowired
    FeignAccountConsumer feignAccountConsumer;


    @PostMapping("accounts/{accountId}/transactions")
    public ResponseEntity<TransactionDto> createUser(@PathVariable("accountId") String accountId,
                                                     @Valid @RequestBody TransactionDto transactionDto,
                                                     HttpServletRequest request) {
        String customerId = (String) request.getHeader("userid");

        if(customerId == null){
            return new ResponseEntity<>(transactionService.createTransaction(transactionDto, accountId), HttpStatus.CREATED);
        }
        else{
            AccountDto accountDto = feignAccountConsumer.getAccount(customerId, accountId);
            if(accountDto.get_id().equals(transactionDto.getAccountId())){
                List<BalanceDto> balanceDto = feignBalanaceConsumer.getAllBalanceByAccountId(0, 2, accountId);
                BalanceDto currentUserBalance = balanceDto.get(0);
                Number newBalance;
                Number transactionAmount = transactionDto.getAmount();
                Number balanceAmount = (Number) currentUserBalance.getAmount();

                if(transactionDto.getType().equals(Type.DEBIT) && (transactionAmount.doubleValue() < balanceAmount.doubleValue())) {
                    newBalance = balanceAmount.doubleValue() - transactionAmount.doubleValue();
                    currentUserBalance.setAmount(newBalance);
                    feignBalanaceConsumer.updateBalance(currentUserBalance, accountId, currentUserBalance.get_id());
                    return new ResponseEntity<>(transactionService.createTransaction(transactionDto, accountId), HttpStatus.CREATED);
                } else if (transactionDto.getType().equals(Type.CREDIT)) {
                    newBalance = balanceAmount.doubleValue() + transactionAmount.doubleValue();
                    currentUserBalance.setAmount(newBalance);
                    feignBalanaceConsumer.updateBalance(currentUserBalance, accountId, currentUserBalance.get_id());
                    return new ResponseEntity<>(transactionService.createTransaction(transactionDto, accountId), HttpStatus.CREATED);
                } else {
                    throw new TransactionAmountException("Transaction amount is more than Balance amount");
                }
            }
            else {
                throw new AccountIdMismatchException("Account ID " + accountId + " is not available for customer " + customerId);
            }
        }
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public List<TransactionDto> getAllTransactionByAccountId(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                             @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
                                                             @PathVariable("accountId") String accountId) {
        return transactionService.getTransactionByAccountId(page, pageSize, accountId);
    }

    @GetMapping("/accounts/{accountId}/transactions/{transactionId}")
    public ResponseEntity<TransactionDto> getTransactionByAccountId(@PathVariable("accountId") String accountId,
                                                                    @PathVariable("transactionId") String transactionId)
            throws TransactionIdNotFoundException, AccountIdMismatchException {
        return new ResponseEntity<>(transactionService.getTransactionIdByAccountId(accountId, transactionId), HttpStatus.OK);
    }

    @DeleteMapping("/accounts/{accountId}/transactions/{transactionId}")
    public ResponseEntity<String> deleteTransactionByAccountId(@PathVariable("accountId") String accountId,
                                                                    @PathVariable("transactionId") String transactionId)
                                                                    throws TransactionIdNotFoundException, AccountIdMismatchException {
        transactionService.deleteTransactionIdByAccountId(accountId, transactionId);
        return new ResponseEntity<>("Transaction deleted successfully", HttpStatus.OK);
    }

}
