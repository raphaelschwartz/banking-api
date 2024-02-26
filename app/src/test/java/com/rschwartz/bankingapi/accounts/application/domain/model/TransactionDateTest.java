package com.rschwartz.bankingapi.accounts.application.domain.model;

import static com.rschwartz.bankingapi.accounts.application.domain.model.AuditDate.DATE_FUTURE_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.AuditDate.DATE_REQUIRED_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TransactionDateTest {

  @Test
  @DisplayName("Should get error when create date is null.")
  void createDateRequiredError() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> new AuditDate(null));

    assertEquals(DATE_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when create date is future.")
  void createDateFutureError() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> new AuditDate(LocalDateTime.now().plusSeconds(10)));

    assertEquals(DATE_FUTURE_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should create a transaction date.")
  void createTransactionDate() {

    final LocalDateTime createDate = LocalDateTime.now().minusSeconds(10);

    final AuditDate result = new AuditDate(createDate);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(createDate, result.getValue());
    });

  }

}
