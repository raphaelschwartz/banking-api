package com.rschwartz.bankingapi.accounts.application.domain.model;

import static com.rschwartz.bankingapi.accounts.application.domain.model.TransactionDetail.DETAIL_MAXIMUM_SIZE_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.TransactionDetail.DETAIL_MINIMUM_SIZE_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.TransactionDetail.DETAIL_REQUIRED_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TransactionDetailTest {

  @DisplayName("Should get error when detail is null.")
  @ParameterizedTest
  @ValueSource(strings = {StringUtils.EMPTY, " "})
  void detailRequiredError(final String detail) {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> new TransactionDetail(detail));

    assertEquals(DETAIL_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when detail has invalid minimum size.")
  void detailMinimumSizeError() {

    final String detail = RandomStringUtils.randomAlphabetic(9);

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> new TransactionDetail(detail));

    assertEquals(DETAIL_MINIMUM_SIZE_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when detail has invalid maximum size.")
  void detailMaximumSizeError() {

    final String detail = RandomStringUtils.randomAlphabetic(51);

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> new TransactionDetail(detail));

    assertEquals(DETAIL_MAXIMUM_SIZE_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should create a transaction detail.")
  void createTransactionDetail() {

    final String detail = RandomStringUtils.randomAlphabetic(10);

    final TransactionDetail result = new TransactionDetail(detail);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(detail, result.getValue());
    });

  }

}
