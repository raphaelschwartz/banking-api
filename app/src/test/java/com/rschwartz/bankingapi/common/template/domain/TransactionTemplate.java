package com.rschwartz.bankingapi.common.template.domain;

import com.rschwartz.bankingapi.accounts.application.domain.model.AccountBalance;
import com.rschwartz.bankingapi.accounts.application.domain.model.AccountId;
import com.rschwartz.bankingapi.accounts.application.domain.model.AuditDate;
import com.rschwartz.bankingapi.accounts.application.domain.model.EntityId;
import com.rschwartz.bankingapi.accounts.application.domain.model.Money;
import com.rschwartz.bankingapi.accounts.application.domain.model.Transaction;
import com.rschwartz.bankingapi.accounts.application.domain.model.TransactionDetail;
import com.rschwartz.bankingapi.accounts.application.domain.model.TransactionKey;
import com.rschwartz.bankingapi.accounts.application.domain.model.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionTemplate {

  public static Transaction getValidRestoreWithdrawTransferTemplate() {

    return Transaction.restore(
        new EntityId(123L),
        TransactionType.WITHDRAW,
        new TransactionDetail("Trânsferência enviada para 67890"),
        new AuditDate(LocalDateTime.now().minusSeconds(2)),
        Money.ofDecimal(BigDecimal.ONE),
        new AccountId(12345L),
        new AccountBalance(Money.ofDecimal(BigDecimal.TEN)),
        new TransactionKey(UUID.randomUUID())
    );
  }

  public static Transaction getValidCreateDebitWithdrawTransferTemplate() {

    return Transaction.createDebit(
        new TransactionDetail("Trânsferência enviada para 67890"),
        Money.ofDecimal(BigDecimal.ONE),
        new AccountId(12345L),
        new AccountBalance(Money.ofDecimal(BigDecimal.TEN))
    );
  }

  public static Transaction getValidRestoreWithdrawTransferTemplate(final BigDecimal amount) {

    return Transaction.restore(
        new EntityId(123L),
        TransactionType.WITHDRAW,
        new TransactionDetail("Trânsferência enviada para 67890"),
        new AuditDate(LocalDateTime.now().minusSeconds(2)),
        Money.ofDecimal(amount),
        new AccountId(12345L),
        new AccountBalance(Money.ofDecimal(BigDecimal.TEN)),
        new TransactionKey(UUID.randomUUID())
    );
  }

}
