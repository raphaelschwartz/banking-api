package com.rschwartz.bankingapi.accounts.aplication.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Person {

  private final Long id;
  private final String fullName;

  private Person(
      final Long id,
      final String fullName
  ) {
    this.id = id;
    this.fullName = fullName;
  }
  public static Person restore(
      final Long id,
      final String fullName
  ) {
    return new Person(id, fullName);
  }

}
