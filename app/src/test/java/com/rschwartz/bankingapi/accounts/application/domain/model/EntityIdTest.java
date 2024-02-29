package com.rschwartz.bankingapi.accounts.application.domain.model;

import static com.rschwartz.bankingapi.accounts.application.domain.model.EntityId.ID_MINIMUM_VALUE_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.EntityId.ID_NEGATIVE_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.EntityId.ID_REQUIRED_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EntityIdTest {

  @Test
  @DisplayName("Should get error when id is null.")
  void idRequiredError() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> new EntityId(null));

    assertEquals(ID_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when id is negative.")
  void idNegativeError() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> new EntityId(-1L));

    assertEquals(ID_NEGATIVE_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when id is less than zero.")
  void idMinimumValueError() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> new EntityId(0L));

    assertEquals(ID_MINIMUM_VALUE_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should create a entity id.")
  void createEntityId() {

    final Long id = 123456L;

    final EntityId result = new EntityId(id);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(id, result.getValue());
    });

  }

}
