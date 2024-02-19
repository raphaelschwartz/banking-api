package com.rschwartz.bankingapi.balance.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

// FIXME add TOSTRING and Getter
@Getter
@ToString
public class Balance {

  // FIXME UUII
  private final Long id;
  private final String accountNumber;
  private final String value;
  // FIXME
  private final String limit;
  // FIXME
  private final LocalDateTime updateDate;

  private Balance(
      final Long id,
      final String accountNumber,
      final String value,
      final String limit,
      final LocalDateTime updateDate
  ) {
    this.id = id;
    this.accountNumber = accountNumber;
    this.value = value;
    this.limit = limit;
    this.updateDate = updateDate;
  }
  public static Balance restore(
      final Long id,
      final String accountNumber,
      final String value,
      final String limit,
      final LocalDateTime updateDate
  ) {
    return new Balance(id, accountNumber, value, limit, updateDate);
  }

}
