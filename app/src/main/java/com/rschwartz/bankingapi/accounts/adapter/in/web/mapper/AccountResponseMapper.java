package com.rschwartz.bankingapi.accounts.adapter.in.web.mapper;

import com.rschwartz.bankingapi.accounts.adapter.in.web.dto.response.AccountResponse;
import com.rschwartz.bankingapi.accounts.application.port.out.dto.AccountOutput;
import org.springframework.stereotype.Component;

@Component
public class AccountResponseMapper {
  public AccountResponse mapOutputToResponse(final AccountOutput output) {

    return new AccountResponse(
        output.getId(),
        output.getOwnerId(),
        output.getNumber(),
        output.getBalance(),
        output.getUpdateDate());
  }

}
