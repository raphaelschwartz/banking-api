package com.rschwartz.bankingapi.accounts.application.domain.model;

import static com.rschwartz.bankingapi.accounts.application.domain.model.AccountId.ACCOUNT_ID_MINIMUM_VALUE_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.AccountId.ACCOUNT_ID_NEGATIVE_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.AccountId.ACCOUNT_ID_REQUIRED_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccountIdTest {

  @Test
  @DisplayName("Should get error when account id is null.")
  void accountIdRequiredError() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> new AccountId(null));

    assertEquals(ACCOUNT_ID_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when account id is negative.")
  void accountIdNegativeError() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> new AccountId(-1L));

    assertEquals(ACCOUNT_ID_NEGATIVE_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when account id is less than zero.")
  void accountIdMinimumValueError() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> new AccountId(0L));

    assertEquals(ACCOUNT_ID_MINIMUM_VALUE_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should create a account id.")
  void createAccountId() {

    final Long accountId = 123456L;

    final AccountId result = new AccountId(accountId);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(accountId, result.getValue());
    });

  }

}
