package com.rschwartz.bankingapi.accounts.aplication.domain;

import com.rschwartz.bankingapi.accounts.aplication.service.exception.BusinessException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Value;

@Getter
public class Transaction {

  private final Long id;

  // FIXME Add Enum
  private final String type;

  private final String detail;

  private final LocalDateTime createDate;

  private final Money amount;

  private final Long accountId;

  private final Money balanceAfter;

  private final UUID transferId;

  private Transaction(
      final Long id,
      final String type,
      final String detail,
      final LocalDateTime createDate,
      final Money amount,
      final Long accountId,
      final Money balanceAfter,
      final UUID transferId
  ) {
    this.id = id;
    this.type = type;
    this.detail = detail;
    this.createDate = createDate;
    this.amount = amount;
    this.accountId = accountId;
    this.balanceAfter = balanceAfter;
    this.transferId = transferId;
  }

  public static Transaction restore(
      final Long id,
      final String type,
      final String detail,
      final LocalDateTime createDate,
      final Money amount,
      final Long accountId,
      final Money balanceAfter,
      final UUID transferId
  ) {
    return new Transaction(id, type, detail, createDate, amount, accountId, balanceAfter, transferId);
  }

  public static List<Transaction> transferBetweenAccounts(final Account accountSource, final Money money,
      final Account accountTarget, final BigDecimal totalDailyMovement) {

    // FIXME Refactor
    validateTransfer(accountSource, money, totalDailyMovement);

    final LocalDateTime now = LocalDateTime.now();
    final UUID transferId = UUID.randomUUID();

    final Transaction transactionIn = new Transaction(
        null,
        "TRANSFER_RECEIVED",
        String.format("Trânsferência recebida de %s", accountSource.getNumber()),
        now,
        money,
        accountTarget.getId(),
        new Money(accountSource.getBalance()).plus(money),
        transferId
    );

    final Transaction transactionOut = new Transaction(
        null,
        "TRANSFER_SENT",
        String.format("Trânsferência enviada para %s", accountTarget.getNumber()),
        now,
        money,
        accountSource.getId(),
        new Money(accountSource.getBalance()).minus(money),
        transferId
    );

    return List.of(transactionIn, transactionOut);
  }

  private static void validateTransfer(final Account account, final Money money, final BigDecimal totalDailyMovement) {

    if (isAccountInactive(account.getStatus())) {
      throw new BusinessException("Account must be active.");
    }

    if (hasLimitIndisponível(account.getBalance(), money)) {
      throw new BusinessException("Account has no limit for available.");
    }

    if (IsTransactionLimitExceeded(account.getLimit(), totalDailyMovement)) {
      throw new BusinessException("Daily limit has been exceeded.");
    }

  }

  private static boolean IsTransactionLimitExceeded(final BigDecimal limit, final BigDecimal totalDailyMovement) {

    final BigDecimal total = totalDailyMovement.add(limit);

    return total.compareTo(limit) == 1;
  }

  private static boolean isAccountInactive(final String status) {
    return !"ACTIVE".equals(status);
  }

  private static boolean hasLimitIndisponível(final BigDecimal balance, final Money money) {
    // TODO
    return false;
  }

  @Value
  public static class ActivityId {

    private final Long value;
  }

}
