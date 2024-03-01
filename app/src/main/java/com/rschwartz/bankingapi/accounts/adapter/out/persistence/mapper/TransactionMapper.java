package com.rschwartz.bankingapi.accounts.adapter.out.persistence.mapper;

import com.rschwartz.bankingapi.accounts.adapter.out.persistence.entity.TransactionJpaEntity;
import com.rschwartz.bankingapi.accounts.application.domain.model.AccountBalance;
import com.rschwartz.bankingapi.accounts.application.domain.model.AccountId;
import com.rschwartz.bankingapi.accounts.application.domain.model.AuditDate;
import com.rschwartz.bankingapi.accounts.application.domain.model.EntityId;
import com.rschwartz.bankingapi.accounts.application.domain.model.Money;
import com.rschwartz.bankingapi.accounts.application.domain.model.Transaction;
import com.rschwartz.bankingapi.accounts.application.domain.model.TransactionDetail;
import com.rschwartz.bankingapi.accounts.application.domain.model.TransactionKey;
import com.rschwartz.bankingapi.accounts.application.domain.model.TransactionType;
import com.rschwartz.bankingapi.common.annotations.Mapper;

@Mapper
public class TransactionMapper {

  public TransactionJpaEntity mapDomainToJpaEntity(final Transaction transaction) {

    return new TransactionJpaEntity(
        null,
        transaction.getType().toString(),
        transaction.getDetail().getValue(),
        transaction.getCreateDate().getValue(),
        transaction.getAmount().getValue(),
        transaction.getAccountId().getValue(),
        transaction.getBalanceAfter().getValue(),
        transaction.getKey().getValue()
    );
  }

  public Transaction mapJpaEntityToDomain(final TransactionJpaEntity entity) {

    TransactionType.valueOf(entity.getType());

    return Transaction.restore(
        new EntityId(entity.getId()),
        TransactionType.valueOf(entity.getType()),
        new TransactionDetail(entity.getDetail()),
        new AuditDate(entity.getCreateDate()),
        new Money(entity.getAmount()),
        new AccountId(entity.getAccountId()),
        new AccountBalance(Money.ofDecimal(entity.getBalanceAfter())),
        new TransactionKey(entity.getKey())
    );
  }

}
