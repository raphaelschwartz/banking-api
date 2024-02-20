package com.rschwartz.bankingapi.accounts.adapter.out.persistence;

import com.rschwartz.bankingapi.accounts.adapter.out.entity.AccountJpaEntity;
import com.rschwartz.bankingapi.accounts.adapter.out.repository.AccountRepository;
import com.rschwartz.bankingapi.accounts.aplication.port.out.LoadAccountPort;
import com.rschwartz.bankingapi.accounts.domain.Account;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoadAccountPersistenceAdapter implements LoadAccountPort {

  private final AccountRepository repository;

  @Override
  public Optional<Account> execute(final String accountNumber) {

    log.info("Loading account: account_number={}", accountNumber);

    // TODO Loading from caching

    return repository.findByNumber(accountNumber)
        .map(this::convert);
  }

  private Account convert(final AccountJpaEntity entity) {

    return Account.restore(
        entity.getId(),
        entity.getExternalId(),
        entity.getOwnerId(),
        entity.getNumber(),
        entity.getBalance(),
        entity.getAvailableLimit(),
        entity.getUpdateDate());
  }

}
