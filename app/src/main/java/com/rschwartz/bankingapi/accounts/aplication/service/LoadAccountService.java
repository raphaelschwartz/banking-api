package com.rschwartz.bankingapi.accounts.aplication.service;

import com.rschwartz.bankingapi.accounts.aplication.port.in.useCase.LoadAccountUseCase;
import com.rschwartz.bankingapi.accounts.aplication.port.out.LoadAccountPort;
import com.rschwartz.bankingapi.accounts.aplication.port.out.dto.AccountOutput;
import com.rschwartz.bankingapi.accounts.domain.Account;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoadAccountService implements LoadAccountUseCase {

  private final LoadAccountPort port;

  @Override
  public Optional<AccountOutput> execute(final String accountNumber) {

    return port.execute(accountNumber)
        .map(this::convert);
  }

  private AccountOutput convert(final Account domain) {

    return new AccountOutput(
        domain.getExternalId(),
        domain.getOwnerId(),
        domain.getAccountNumber(),
        domain.getValue(),
        domain.getLimit(),
        domain.getUpdateDate());
  }

}
