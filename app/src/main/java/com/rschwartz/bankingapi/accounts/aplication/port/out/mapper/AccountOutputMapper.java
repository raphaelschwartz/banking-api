package com.rschwartz.bankingapi.accounts.aplication.port.out.mapper;

import com.rschwartz.bankingapi.accounts.aplication.domain.Account;
import com.rschwartz.bankingapi.accounts.aplication.port.out.dto.AccountOutput;
import org.springframework.stereotype.Component;

@Component
public class AccountOutputMapper {

  public AccountOutput mapDomainToOutput(final Account domain) {

    return new AccountOutput(
        domain.getId(),
        domain.getOwnerId(),
        domain.getNumber(),
        domain.getBalance(),
        domain.getLimit(),
        domain.getUpdateDate());
  }

}
