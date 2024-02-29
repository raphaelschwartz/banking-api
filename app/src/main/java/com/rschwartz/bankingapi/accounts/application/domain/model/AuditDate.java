package com.rschwartz.bankingapi.accounts.application.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;

@Getter
public class AuditDate {

  protected static final String DATE_REQUIRED_ERROR_MESSAGE = "Must provide a date.";
  protected static final String DATE_FUTURE_ERROR_MESSAGE = "Date must be in the past.";

  private final LocalDateTime value;

  public AuditDate(final LocalDateTime date) {
    validate(date);
    this.value = date;
  }

  private void validate(final LocalDateTime date) {

    if (Objects.isNull(date)) {
      throw new IllegalArgumentException(DATE_REQUIRED_ERROR_MESSAGE);
    }

    if (isFuture(date)) {
      throw new IllegalArgumentException(DATE_FUTURE_ERROR_MESSAGE);
    }

  }

  private boolean isFuture(final LocalDateTime date) {
    return date.isAfter(LocalDateTime.now());
  }

}
