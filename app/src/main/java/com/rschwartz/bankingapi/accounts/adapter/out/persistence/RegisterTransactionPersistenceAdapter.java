package com.rschwartz.bankingapi.accounts.adapter.out.persistence;

import com.rschwartz.bankingapi.accounts.adapter.out.persistence.entity.TransactionJpaEntity;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.mapper.TransactionMapper;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.repository.TransactionRepository;
import com.rschwartz.bankingapi.accounts.application.domain.model.Transaction;
import com.rschwartz.bankingapi.accounts.application.port.out.RegisterTransactionPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegisterTransactionPersistenceAdapter implements RegisterTransactionPort {

  private final TransactionMapper mapper;
  private final TransactionRepository repository;

  @Override
  public Transaction save(final Transaction transaction) {

    log.info("Registering transaction: {}", transaction);

    final TransactionJpaEntity entity = mapper.mapDomainToJpaEntity(transaction);

    return mapper.mapJpaEntityToDomain(repository.save(entity));
  }

}
