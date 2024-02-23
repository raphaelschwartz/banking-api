package com.rschwartz.bankingapi.accounts.aplication.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Account {

  private final Long id;
  private final Long ownerId;
  private final String number;
  private final BigDecimal balance;
  private final BigDecimal limit;
  private final LocalDateTime updateDate;
  private final String status;

  private Account(
      final Long id,
      final Long ownerId,
      final String number,
      final BigDecimal balance,
      final BigDecimal limit,
      final LocalDateTime updateDate,
      final String status
  ) {
    this.id = id;
    this.ownerId = ownerId;
    this.number = number;
    this.balance = balance;
    this.limit = limit;
    this.updateDate = updateDate;
    this.status = status;
  }
  public static Account restore(
      final Long id,
      final Long ownerId,
      final String number,
      final BigDecimal balance,
      final BigDecimal limit,
      final LocalDateTime updateDate,
      final String status
  ) {
    return new Account(id, ownerId, number, balance, limit, updateDate, status);
  }

  public void updateBalance() {
  // FIXME must send domain event
  }

}
