package com.rschwartz.bankingapi.accounts.application.port.out.mapper;

import com.rschwartz.bankingapi.accounts.application.domain.model.Account;
import com.rschwartz.bankingapi.accounts.application.port.out.dto.AccountOutput;
import com.rschwartz.bankingapi.common.annotations.Mapper;

@Mapper
public class AccountOutputMapper {

  public AccountOutput mapDomainToOutput(final Account domain) {

    return new AccountOutput(
        domain.getId().getValue(),
        domain.getOwnerId().getValue(),
        domain.getNumber(),
        domain.getBalance().getValue(),
        domain.getUpdateDate().getValue());
  }

}
