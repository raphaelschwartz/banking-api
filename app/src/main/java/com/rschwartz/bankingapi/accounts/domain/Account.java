package com.rschwartz.bankingapi.accounts.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Account {

  private final Long id;
  private final UUID externalId;
  private final UUID ownerId;
  private final String accountNumber;
  private final BigDecimal value;
  private final BigDecimal limit;
  private final LocalDateTime updateDate;

  private Account(
      final Long id,
      final UUID externalId,
      final UUID ownerId,
      final String accountNumber,
      final BigDecimal value,
      final BigDecimal limit,
      final LocalDateTime updateDate
  ) {
    this.id = id;
    this.externalId = externalId;
    this.ownerId = ownerId;
    this.accountNumber = accountNumber;
    this.value = value;
    this.limit = limit;
    this.updateDate = updateDate;
  }
  public static Account restore(
      final Long id,
      final UUID externalId,
      final UUID ownerId,
      final String number,
      final BigDecimal balance,
      final BigDecimal limit,
      final LocalDateTime updateDate
  ) {
    return new Account(id, externalId, ownerId, number, balance, limit, updateDate);
  }

}
