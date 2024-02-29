package com.rschwartz.bankingapi.accounts.application.domain.model;

import static com.rschwartz.bankingapi.accounts.application.domain.model.AccountBalance.AMOUNT_REQUIRED_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.AccountBalance.BALANCE_REQUIRED_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.AccountBalance.INSUFFICIENT_FUNDS_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccountBalanceTest {

  @Test
  @DisplayName("Should get error when balance is null.")
  void balanceRequiredError() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> new AccountBalance(null));

    assertEquals(BALANCE_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should create a account balance.")
  void createAccountBalance() {

    final Money money = Money.ofDecimal(BigDecimal.TEN);

    final AccountBalance result = new AccountBalance(money);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(money.getValue(), result.getValue());
    });

  }

  @Test
  @DisplayName("Should get error in debit when money is null.")
  void moneyRequiredErrorInDebit() {

    final AccountBalance accountBalance = new AccountBalance(Money.ofDecimal(BigDecimal.TEN));

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> accountBalance.debit(null));

    assertEquals(AMOUNT_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error in debit when there is no limit available.")
  void limitAvailableExceededErrorInDebit() {

    final Money money = Money.ofInteger(25);
    final AccountBalance accountBalance = new AccountBalance(Money.ofInteger(20));

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> accountBalance.debit(money));

    assertEquals(INSUFFICIENT_FUNDS_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should debit a money to balance.")
  void createAccountBalanceDebit() {

    final Money money = Money.ofDecimal(BigDecimal.ONE);
    final AccountBalance accountBalance = new AccountBalance(Money.ofDecimal(BigDecimal.TEN));

    final AccountBalance result = accountBalance.debit(money);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(new BigDecimal(9), result.getValue());
    });
  }

  @Test
  @DisplayName("Should get error in credit when money is null.")
  void moneyRequiredErrorInCredit() {

    final AccountBalance accountBalance = new AccountBalance(Money.ofDecimal(BigDecimal.TEN));

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> accountBalance.credit(null));

    assertEquals(AMOUNT_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should credit a money to balance.")
  void createAccountBalanceCredit() {

    final Money money = Money.ofDecimal(BigDecimal.ONE);
    final AccountBalance accountBalance = new AccountBalance(Money.ofDecimal(BigDecimal.TEN));

    final AccountBalance result = accountBalance.credit(money);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(new BigDecimal(11), result.getValue());
    });
  }

}
