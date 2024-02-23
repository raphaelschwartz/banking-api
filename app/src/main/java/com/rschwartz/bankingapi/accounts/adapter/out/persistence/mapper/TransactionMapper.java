package com.rschwartz.bankingapi.accounts.adapter.out.persistence.mapper;

import com.rschwartz.bankingapi.accounts.adapter.out.persistence.entity.TransactionJpaEntity;
import com.rschwartz.bankingapi.accounts.aplication.domain.Money;
import com.rschwartz.bankingapi.accounts.aplication.domain.Transaction;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

  public TransactionJpaEntity mapToJpaEntity(final Transaction transaction) {

    return new TransactionJpaEntity(
        transaction.getId(),
        transaction.getType(),
        transaction.getDetail(),
        transaction.getCreateDate(),
        transaction.getAmount().getAmount(),
        transaction.getAccountId(),
        transaction.getBalanceAfter().getAmount(),
        UUID.randomUUID()
    );
  }

  public Transaction mapJpaEntityToDomain(final TransactionJpaEntity entity) {

    return Transaction.restore(
        entity.getId(),
        entity.getType(),
        entity.getDetail(),
        entity.getCreateDate(),
        new Money(entity.getAmount()),
        entity.getAccountId(),
        new Money(entity.getBalanceAfter()),
        entity.getTransferKey()
    );
  }

}
