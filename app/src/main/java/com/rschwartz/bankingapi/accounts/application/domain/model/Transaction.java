package com.rschwartz.bankingapi.accounts.application.domain.model;

import static java.util.Objects.isNull;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class Transaction {

  protected static final String ID_REQUIRED_ERROR_MESSAGE = "Must provide a id.";
  protected static final String TYPE_REQUIRED_ERROR_MESSAGE = "Must provide a type.";
  protected static final String DETAIL_REQUIRED_ERROR_MESSAGE = "Must provide a detail.";
  protected static final String CREATE_DATE_REQUIRED_ERROR_MESSAGE = "Must provide a createDate.";
  protected static final String AMOUNT_REQUIRED_ERROR_MESSAGE = "Must provide a amount.";
  protected static final String ACCOUNT_ID_REQUIRED_ERROR_MESSAGE = "Must provide a accountId.";
  protected static final String BALANCE_REQUIRED_ERROR_MESSAGE = "Must provide a balance.";
  protected static final String KEY_REQUIRED_ERROR_MESSAGE = "Must provide a money.";

  private final EntityId id;

  private final TransactionType type;

  private final TransactionDetail detail;

  private final AuditDate createDate;

  private final Money amount;

  private final AccountId accountId;

  private final AccountBalance balanceAfter;

  private final TransactionKey key;

  private Transaction(
      final EntityId id,
      final TransactionType type,
      final TransactionDetail detail,
      final AuditDate createDate,
      final Money amount,
      final AccountId accountId,
      final AccountBalance balanceAfter,
      final TransactionKey key
  ) {
    this.id = id;
    this.type = type;
    this.detail = detail;
    this.createDate = createDate;
    this.amount = amount;
    this.accountId = accountId;
    this.balanceAfter = balanceAfter;
    this.key = key;
  }

  public static Transaction createDebit(
      final TransactionDetail detail,
      final Money amount,
      final AccountId accountId,
      final AccountBalance balanceAfter
  ) {
    validateCreateDebit(detail, amount, accountId, balanceAfter);

    return new Transaction(null, TransactionType.WITHDRAW, detail,
        new AuditDate(LocalDateTime.now()), amount, accountId, balanceAfter,
        new TransactionKey(UUID.randomUUID()));
  }

  private static void validateCreateDebit(
      final TransactionDetail detail,
      final Money amount,
      final AccountId accountId,
      final AccountBalance balanceAfter
  ) {

    if (isNull(detail)) {
      throw new IllegalArgumentException(DETAIL_REQUIRED_ERROR_MESSAGE);
    }

    if (isNull(amount)) {
      throw new IllegalArgumentException(AMOUNT_REQUIRED_ERROR_MESSAGE);
    }

    if (isNull(accountId)) {
      throw new IllegalArgumentException(ACCOUNT_ID_REQUIRED_ERROR_MESSAGE);
    }

    if (isNull(balanceAfter)) {
      throw new IllegalArgumentException(BALANCE_REQUIRED_ERROR_MESSAGE);
    }

  }

  public static Transaction createCredit(
      final TransactionDetail detail,
      final Money amount,
      final AccountId accountId,
      final AccountBalance balanceAfter,
      final TransactionKey key
  ) {
    validateCreateCredit(detail, amount, accountId, balanceAfter, key);

    return new Transaction(null, TransactionType.DEPOSIT, detail,
        new AuditDate(LocalDateTime.now()), amount, accountId, balanceAfter, key);
  }

  private static void validateCreateCredit(
      final TransactionDetail detail,
      final Money amount,
      final AccountId accountId,
      final AccountBalance balanceAfter,
      final TransactionKey key
  ) {

    if (isNull(detail)) {
      throw new IllegalArgumentException(DETAIL_REQUIRED_ERROR_MESSAGE);
    }

    if (isNull(amount)) {
      throw new IllegalArgumentException(AMOUNT_REQUIRED_ERROR_MESSAGE);
    }

    if (isNull(accountId)) {
      throw new IllegalArgumentException(ACCOUNT_ID_REQUIRED_ERROR_MESSAGE);
    }

    if (isNull(balanceAfter)) {
      throw new IllegalArgumentException(BALANCE_REQUIRED_ERROR_MESSAGE);
    }

    if (isNull(key)) {
      throw new IllegalArgumentException(KEY_REQUIRED_ERROR_MESSAGE);
    }

  }

  public static Transaction restore(
      final EntityId id,
      final TransactionType type,
      final TransactionDetail detail,
      final AuditDate createDate,
      final Money amount,
      final AccountId accountId,
      final AccountBalance balanceAfter,
      final TransactionKey key
  ) {
    validateRestore(id, type, detail, createDate, amount, accountId, balanceAfter, key);

    return new Transaction(id, type, detail, createDate, amount, accountId, balanceAfter, key);
  }

  private static void validateRestore(
      final EntityId id,
      final TransactionType type,
      final TransactionDetail detail,
      final AuditDate createDate,
      final Money amount,
      final AccountId accountId,
      final AccountBalance balanceAfter,
      final TransactionKey key
  ) {

    if (isNull(id)) {
      throw new IllegalArgumentException(ID_REQUIRED_ERROR_MESSAGE);
    }

    if (isNull(type)) {
      throw new IllegalArgumentException(TYPE_REQUIRED_ERROR_MESSAGE);
    }

    if (isNull(detail)) {
      throw new IllegalArgumentException(DETAIL_REQUIRED_ERROR_MESSAGE);
    }

    if (isNull(createDate)) {
      throw new IllegalArgumentException(CREATE_DATE_REQUIRED_ERROR_MESSAGE);
    }

    if (isNull(amount)) {
      throw new IllegalArgumentException(AMOUNT_REQUIRED_ERROR_MESSAGE);
    }

    if (isNull(accountId)) {
      throw new IllegalArgumentException(ACCOUNT_ID_REQUIRED_ERROR_MESSAGE);
    }

    if (isNull(balanceAfter)) {
      throw new IllegalArgumentException(BALANCE_REQUIRED_ERROR_MESSAGE);
    }

    if (isNull(key)) {
      throw new IllegalArgumentException(KEY_REQUIRED_ERROR_MESSAGE);
    }

  }

}
