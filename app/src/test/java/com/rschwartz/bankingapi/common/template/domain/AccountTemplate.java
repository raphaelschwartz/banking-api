package com.rschwartz.bankingapi.common.template.domain;

import com.rschwartz.bankingapi.accounts.domain.Account;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class AccountTemplate {

  public static final String VALID = "valid";

  public static Account getValidTemplate() {

    return Account.restore(
        1L,
        UUID.randomUUID(),
        UUID.randomUUID(),
        "0123456",
        new BigDecimal("10.80"),
        new BigDecimal("100.000"),
        LocalDateTime.now().minusMinutes(30));
  }

}
