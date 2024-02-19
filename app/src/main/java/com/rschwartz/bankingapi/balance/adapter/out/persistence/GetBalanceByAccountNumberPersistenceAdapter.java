package com.rschwartz.bankingapi.balance.adapter.out.persistence;

import com.rschwartz.bankingapi.balance.adapter.out.entity.BalanceJpaEntity;
import com.rschwartz.bankingapi.balance.adapter.out.repository.BalanceRepository;
import com.rschwartz.bankingapi.balance.application.port.out.GetBalanceByAccountNumberPort;
import com.rschwartz.bankingapi.balance.domain.Balance;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetBalanceByAccountNumberPersistenceAdapter implements GetBalanceByAccountNumberPort {

  private final BalanceRepository repository;

  @Override
  public Optional<Balance> execute(final String accountNumber) {

    log.info("Checking balance: account_number={}", accountNumber);

    return repository.findByAccountNumber(accountNumber)
        .map(this::convert);
  }

  private Balance convert(final BalanceJpaEntity entity) {

    return Balance.restore(
        entity.getId(),
        entity.getAccountNumber(),
        entity.getValue(),
        entity.getLimit(),
        entity.getUpdateDate());
  }

}
