package com.rschwartz.bankingapi.accounts.application.domain.model;

import static com.rschwartz.bankingapi.accounts.application.domain.model.TransactionKey.KEY_REQUIRED_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TransactionKeyTest {

  @Test
  @DisplayName("Should get error when key is null.")
  void keyRequiredError() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> new TransactionKey(null));

    assertEquals(KEY_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should create a transaction key.")
  void createTransactionKey() {

    final UUID key = UUID.randomUUID();

    final TransactionKey result = new TransactionKey(key);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(key, result.getValue());
    });

  }

}
