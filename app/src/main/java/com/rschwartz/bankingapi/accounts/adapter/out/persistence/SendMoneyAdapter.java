package com.rschwartz.bankingapi.accounts.adapter.out.persistence;

import com.rschwartz.bankingapi.accounts.adapter.out.repository.AccountRepository;
import com.rschwartz.bankingapi.accounts.aplication.port.out.SendMoneyPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SendMoneyAdapter implements SendMoneyPort {

  private final AccountRepository repository;

  @Override
  public void execute(final String accountNumber) {

    log.info("Checking balance: account_number={}", accountNumber);


  }
/*
  private Account convert(final AccountJpaEntity entity) {

    return Account.restore(
        entity.getId(),
        entity.getAccountNumber(),
        entity.getValue(),
        entity.getLimit(),
        entity.getUpdateDate());
  }

 */

}
