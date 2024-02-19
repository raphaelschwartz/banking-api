package com.rschwartz.bankingapi.common.template.domain;

import com.rschwartz.bankingapi.balance.domain.Balance;
import java.time.LocalDateTime;

public class BalanceTemplate {

  public static final String VALID = "valid";

  public static Balance getValidTemplate() {

    return Balance.restore(
        1L,
        "0123456",
        "10.80",
        "100",
        LocalDateTime.now().minusMinutes(30));
  }

}
