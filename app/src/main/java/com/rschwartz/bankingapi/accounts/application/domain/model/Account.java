package com.rschwartz.bankingapi.accounts.application.domain.model;

import static java.util.Objects.isNull;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Account {

  protected static final String ID_REQUIRED_ERROR_MESSAGE = "Must provide a id.";
  protected static final String OWNER_ID_REQUIRED_ERROR_MESSAGE = "Must provide a owner id.";
  protected static final String NUMBER_REQUIRED_ERROR_MESSAGE = "Must provide a number.";
  protected static final String BALANCE_REQUIRED_ERROR_MESSAGE = "Must provide a balance.";
  protected static final String UPDATE_DATE_REQUIRED_ERROR_MESSAGE = "Must provide a update date.";
  protected static final String STATUS_REQUIRED_ERROR_MESSAGE = "Must provide a account status.";

  protected static final String ACCOUNT_NUMBER_REQUIRED_ERROR_MESSAGE = "Must provide a account number.";
  protected static final String MONEY_REQUIRED_ERROR_MESSAGE = "Must provide a money.";
  protected static final String AMOUNT_MINIMUM_ERROR_MESSAGE = "Amount must be greater than zero.";
  protected static final String ACCOUNT_NUMBER_IDENTICAL_ERROR_MESSAGE = "Must provide a different account number.";
  protected static final String TRANSACTION_KEY_REQUIRED_ERROR_MESSAGE = "Must provide a transaction key.";

  private final AccountId id;
  private final EntityId ownerId;
  private final String number;
  private AccountBalance balance;
  private final AuditDate updateDate;
  private final AccountStatus status;

  private Account(
      final AccountId id,
      final EntityId ownerId,
      final String number,
      final AccountBalance balance,
      final AuditDate updateDate,
      final AccountStatus status
  ) {
    this.id = id;
    this.ownerId = ownerId;
    this.number = number;
    this.balance = balance;
    this.updateDate = updateDate;
    this.status = status;
  }

  public static Account restore(
      final AccountId id,
      final EntityId ownerId,
      final String number,
      final AccountBalance balance,
      final AuditDate updateDate,
      final AccountStatus status
  ) {
    validateRestore(id, ownerId, number, balance, updateDate, status);

    return new Account(id, ownerId, number, balance, updateDate, status);
  }

  private static void validateRestore(
      final AccountId id,
      final EntityId ownerId,
      final String number,
      final AccountBalance balance,
      final AuditDate updateDate,
      final AccountStatus status
  ) {

    if (isNull(id)) {
      throw new IllegalArgumentException(ID_REQUIRED_ERROR_MESSAGE);
    }

    if (isNull(ownerId)) {
      throw new IllegalArgumentException(OWNER_ID_REQUIRED_ERROR_MESSAGE);
    }

    if (isNull(number)) {
      throw new IllegalArgumentException(NUMBER_REQUIRED_ERROR_MESSAGE);
    }

    if (isNull(balance)) {
      throw new IllegalArgumentException(BALANCE_REQUIRED_ERROR_MESSAGE);
    }

    if (isNull(updateDate)) {
      throw new IllegalArgumentException(UPDATE_DATE_REQUIRED_ERROR_MESSAGE);
    }

    if (isNull(status)) {
      throw new IllegalArgumentException(STATUS_REQUIRED_ERROR_MESSAGE);
    }

  }

  public Transaction withdraw(final Money money, final String targetAccountNumber) {

    validateWithdraw(money, targetAccountNumber);

    final AccountBalance currentBalance = this.balance.debit(money);
    updateBalance(currentBalance);

    return Transaction.createDebit(
        new TransactionDetail(String.format("Trânsferência enviada para %s", targetAccountNumber)),
        money,
        this.id,
        currentBalance
    );
  }

  private void validateWithdraw(final Money money, final String targetAccountNumber) {

    if (isNull(money)) {
      throw new IllegalArgumentException(MONEY_REQUIRED_ERROR_MESSAGE);
    }

    if (isEqualToZero(money)) {
      throw new IllegalArgumentException(AMOUNT_MINIMUM_ERROR_MESSAGE);
    }

    if (isNull(targetAccountNumber)) {
      throw new IllegalArgumentException(ACCOUNT_NUMBER_REQUIRED_ERROR_MESSAGE);
    }

    if (isSameAccount(targetAccountNumber)) {
      throw new IllegalArgumentException(ACCOUNT_NUMBER_IDENTICAL_ERROR_MESSAGE);
    }

  }

  private void updateBalance(final AccountBalance balance) {
    this.balance = balance;
  }

  public Transaction deposit(
      final Money money,
      final String sourceAccountNumber,
      final TransactionKey key
  ) {

    validateDeposit(money, sourceAccountNumber, key);

    final AccountBalance currentBalance = this.balance.credit(money);
    updateBalance(currentBalance);

    return Transaction.createCredit(
        new TransactionDetail(String.format("Trânsferência recebida de %s", sourceAccountNumber)),
        money,
        this.id,
        currentBalance,
        key
    );
  }

  private void validateDeposit(
      final Money money,
      final String sourceAccountNumber,
      final TransactionKey key
  ) {

    if (isNull(money)) {
      throw new IllegalArgumentException(MONEY_REQUIRED_ERROR_MESSAGE);
    }

    if (isEqualToZero(money)) {
      throw new IllegalArgumentException(AMOUNT_MINIMUM_ERROR_MESSAGE);
    }

    if (isNull(sourceAccountNumber)) {
      throw new IllegalArgumentException(ACCOUNT_NUMBER_REQUIRED_ERROR_MESSAGE);
    }

    if (isSameAccount(sourceAccountNumber)) {
      throw new IllegalArgumentException(ACCOUNT_NUMBER_IDENTICAL_ERROR_MESSAGE);
    }

    if (isNull(key)) {
      throw new IllegalArgumentException(TRANSACTION_KEY_REQUIRED_ERROR_MESSAGE);
    }

  }

  private boolean isEqualToZero(final Money money) {
    return money.getValue().compareTo(BigDecimal.ZERO) == 0;
  }

  private boolean isSameAccount(final String accountNumber) {
    return number.equals(accountNumber);
  }

}
