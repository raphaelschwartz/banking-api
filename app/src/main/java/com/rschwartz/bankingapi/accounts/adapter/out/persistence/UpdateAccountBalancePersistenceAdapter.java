package com.rschwartz.bankingapi.accounts.adapter.out.persistence;

import com.rschwartz.bankingapi.accounts.adapter.out.persistence.entity.AccountJpaEntity;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.mapper.AccountMapper;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.repository.AccountRepository;
import com.rschwartz.bankingapi.accounts.application.domain.model.Account;
import com.rschwartz.bankingapi.accounts.application.port.out.UpdateAccountBalancePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateAccountBalancePersistenceAdapter implements UpdateAccountBalancePort {

  private final AccountMapper mapper;
  private final AccountRepository repository;

  @Override
  public Account save(final Account account) {

    log.info("Updating account: {}", account);

    final AccountJpaEntity entity = mapper.mapDomainToJpaEntity(account);

    return mapper.mapJpaEntityToDomain(repository.save(entity));
  }

}
