package com.rschwartz.bankingapi.accounts.application.domain.model;

import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public class TransactionKey {

  protected static final String KEY_REQUIRED_ERROR_MESSAGE = "Must provide a key.";

  private final UUID value;

  public TransactionKey(final UUID key) {
    validate(key);
    this.value = key;
  }

  private void validate(final UUID key) {

    if (Objects.isNull(key)) {
      throw new IllegalArgumentException(KEY_REQUIRED_ERROR_MESSAGE);
    }

  }

}
