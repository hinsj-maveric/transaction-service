package com.maveric.transactionservice.feignclient;

import com.maveric.transactionservice.dto.BalanceDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "feign-balance",url = "http://localhost:3015/api/v1/")
public interface FeignBalanaceConsumer {

    @GetMapping("accounts/{accountId}/balances/{balanceId}")
    public ResponseEntity<BalanceDto> getBalanceByAccountId(@PathVariable("accountId") String accountId,
                                                            @PathVariable("balanceId") String balanceId,
                                                            @RequestHeader(value = "userid") String headerUserId);

    @GetMapping("accounts/{accountId}/balances")
    public List<BalanceDto> getAllBalanceByAccountId(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                     @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
                                                     @PathVariable("accountId") @Valid String accountId,
                                                     @RequestHeader(value = "userid") String headerUserId);

    @PutMapping("/accounts/{accountId}/balances/{balanceId}")
    public ResponseEntity<BalanceDto> updateBalance(@Valid @RequestBody BalanceDto balanceDto,
                                                    @PathVariable String accountId, @PathVariable String balanceId,
                                                    @RequestHeader(value = "userid") String headerUserId);

}
