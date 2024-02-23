package com.rschwartz.bankingapi.common.template.domain;

import com.rschwartz.bankingapi.accounts.aplication.domain.Account;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountTemplate {

  public static Account getValidTemplate() {

    return Account.restore(
        1L,
        1234L,
        "0123456",
        new BigDecimal("10.80"),
        new BigDecimal("100.000"),
        LocalDateTime.now().minusMinutes(30),
        "ACTIVE");
  }

}
