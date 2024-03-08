package com.rschwartz.bankingapi.accounts.application.port.in.command;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rschwartz.bankingapi.common.application.port.in.command.CommandValidation;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SendMoneyCommandTest extends CommandValidation {

  private static final String FIELD_SOURCE_ACCOUNT_ID = "sourceAccountId";
  private static final String FIELD_TARGET_ACCOUNT_ID = "targetAccountId";
  private static final String FIELD_AMOUNT = "amount";

  private static final String AMOUNT_FAILURE_MESSAGE = "must be greater than zero";

  @Test
  @DisplayName("Should get error when required fields are null.")
  void notNullError() {

    final ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
        () -> new SendMoneyCommand(
            null,
            null,
            null
        ));

    final Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
    assertAll(() -> {
      assertNotNull(violations);
      assertEquals(3, violations.size());
      assertTrue(hasError(violations, FIELD_SOURCE_ACCOUNT_ID, MESSAGE_TEMPLATE_NOT_NULL));
      assertTrue(hasError(violations, FIELD_TARGET_ACCOUNT_ID, MESSAGE_TEMPLATE_NOT_NULL));
      assertTrue(hasError(violations, FIELD_AMOUNT, MESSAGE_TEMPLATE_NOT_NULL));
    });
  }

  @DisplayName("Should get error when sourceAccountId has invalid value.")
  @ParameterizedTest
  @ValueSource(longs = {0, -1})
  void sourceAccountIdValidationFails(final Long id) {

    final ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
        () -> new SendMoneyCommand(
            id,
            1L,
            new BigDecimal(10)
        ));

    final Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
    assertAll(() -> {
      assertNotNull(violations);
      assertEquals(1, violations.size());
      assertTrue(hasError(violations, FIELD_SOURCE_ACCOUNT_ID, MESSAGE_MIN));
    });
  }

  @DisplayName("Should get error when targetAccountI has invalid value.")
  @ParameterizedTest
  @ValueSource(longs = {0, -1})
  void targetAccountIdValidationFails(final Long id) {

    final ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
        () -> new SendMoneyCommand(
            1L,
            id,
            new BigDecimal(10)
        ));

    final Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
    assertAll(() -> {
      assertNotNull(violations);
      assertEquals(1, violations.size());
      assertTrue(hasError(violations, FIELD_TARGET_ACCOUNT_ID, MESSAGE_MIN));
    });
  }

  @DisplayName("Should get error when amount has invalid value.")
  @ParameterizedTest
  @ValueSource(doubles = {0.00, -1.00})
  void amountValidationFails(final Double amount) {

    final ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
        () -> new SendMoneyCommand(
            12345L,
            67890L,
            BigDecimal.valueOf(amount)
        ));

    final Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
    assertAll(() -> {
      assertNotNull(violations);
      assertEquals(1, violations.size());
      assertTrue(hasError(violations, FIELD_AMOUNT, AMOUNT_FAILURE_MESSAGE));
    });
  }

  @DisplayName("Should validate a command.")
  @Test
  void validationOk() {

    final Long sourceAccountId = 12345L;
    final Long targetAccountId = 67890L;
    final BigDecimal amount = new BigDecimal("0.10");

    final SendMoneyCommand result = new SendMoneyCommand(
        sourceAccountId,
        targetAccountId,
        amount);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(sourceAccountId, result.sourceAccountId());
      assertEquals(targetAccountId, result.targetAccountId());
      assertEquals(amount, result.amount());
    });

  }

}
