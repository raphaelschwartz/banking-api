package com.rschwartz.bankingapi.common.template.domain;

import com.rschwartz.bankingapi.accounts.application.domain.Person;

public class PersonTemplate {

  public static Person getValidTemplate() {

    return Person.restore(
        1L,
        "Name Fake");
  }

}
