package com.rschwartz.bankingapi.accounts.application.domain.model;

import static com.rschwartz.bankingapi.accounts.application.domain.model.Money.AMOUNT_NEGATIVE_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.Money.AMOUNT_REQUIRED_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoneyTest {

  @Test
  @DisplayName("Should get error when amount is null.")
  void amountRequiredError() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> new Money(null));

    assertEquals(AMOUNT_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when amount is negative.")
  void amountNegativeError() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Money.ofInteger(-1));

    assertEquals(AMOUNT_NEGATIVE_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should create a money.")
  void createMoney() {

    final BigDecimal amount = BigDecimal.TEN;

    final Money result = Money.ofDecimal(amount);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(amount, result.getValue());
    });

  }

  @Test
  @DisplayName("Should create a money using of decimal number.")
  void createMoneyWithOfDecimal() {

    final BigDecimal amount = BigDecimal.TEN;

    final Money result = Money.ofDecimal(amount);
    final Money moneyExpected = Money.ofDecimal(BigDecimal.TEN);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(moneyExpected.getValue(), result.getValue());
    });

  }

  @Test
  @DisplayName("Should create a money using of integer number.")
  void createMoneyWithOfIntegerNuber() {

    final BigDecimal amount = BigDecimal.TEN;

    final Money result = Money.ofDecimal(amount);
    final Money moneyExpected = Money.ofInteger(10);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(moneyExpected.getValue(), result.getValue());
    });

  }

  @Test
  @DisplayName("Should subtract a money.")
  void subtractMoney() {

    final BigDecimal amount = BigDecimal.TEN;
    final BigDecimal amount1 = BigDecimal.ONE;
    final Money money = new Money(amount1);

    final Money result = Money.ofDecimal(amount).minus(money);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(new Money(new BigDecimal(9)).getValue(), result.getValue());
    });

  }

  @Test
  @DisplayName("Should add a money.")
  void addMoney() {

    final BigDecimal amount = BigDecimal.TEN;
    final BigDecimal amount1 = BigDecimal.ONE;
    final Money money = new Money(amount1);

    final Money result = Money.ofDecimal(amount).plus(money);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(new Money(new BigDecimal(11)).getValue(), result.getValue());
    });

  }

  @Test
  @DisplayName("Should get true when money is greater than another money.")
  void moneyIsGreaterThan() {

    final BigDecimal amount = BigDecimal.TEN;
    final BigDecimal amount1 = BigDecimal.ONE;
    final Money money = Money.ofDecimal(amount1);

    assertTrue(new Money(amount).isGreaterThan(money));
  }

  @Test
  @DisplayName("Should get false when money is greater than another money.")
  void moneyIsNotGreaterThan() {

    final BigDecimal amount = BigDecimal.TEN;
    final BigDecimal amount1 = BigDecimal.TEN;
    final Money money = Money.ofDecimal(amount1);

    assertFalse(Money.ofInteger(10).isGreaterThan(money));
  }

}
