package com.rschwartz.bankingapi.accounts.adapter.out.persistence;

import com.rschwartz.bankingapi.accounts.adapter.out.persistence.mapper.AccountMapper;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.repository.AccountRepository;
import com.rschwartz.bankingapi.accounts.application.domain.model.Account;
import com.rschwartz.bankingapi.accounts.application.port.out.LoadAccountPort;
import com.rschwartz.bankingapi.common.annotations.PersistenceAdapter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@PersistenceAdapter
@Slf4j
@RequiredArgsConstructor
public class LoadAccountPersistenceAdapter implements LoadAccountPort {

  private final AccountRepository repository;
  private final AccountMapper mapper;

  @Override
  public Optional<Account> findById(final Long id) {

    log.info("Loading account: account_id={}", id);

    // TODO Loading from caching

    return repository.findByIdAndActiveTrue(id)
        .map(mapper::mapJpaEntityToDomain);
  }

}
