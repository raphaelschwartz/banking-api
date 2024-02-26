package com.rschwartz.bankingapi.accounts.adapter.out.persistence;

import com.rschwartz.bankingapi.accounts.adapter.out.persistence.entity.TransactionJpaEntity;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.mapper.TransactionMapper;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.repository.TransactionRepository;
import com.rschwartz.bankingapi.accounts.application.domain.model.AccountId;
import com.rschwartz.bankingapi.accounts.application.domain.model.Transaction;
import com.rschwartz.bankingapi.accounts.application.port.out.SearchTransactionsPort;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchTransactionsPersistenceAdapter implements SearchTransactionsPort {

  private final TransactionRepository repository;
  private final TransactionMapper mapper;

  @Override
  public List<Transaction> search(final AccountId id, final LocalDateTime baselineDate) {

    log.info("Searching for account_id={} date={}.", id.getValue(), baselineDate);

    final List<TransactionJpaEntity> transactions = repository.findAllByAccountIdAndCreateDateGreaterThanEqual(
        id.getValue(), baselineDate);

    log.info("Transaction search completed successfully. Returned {} record(s).",
        transactions.size());

    return transactions.stream()
        .map(mapper::mapJpaEntityToDomain)
        .toList();
  }

}
