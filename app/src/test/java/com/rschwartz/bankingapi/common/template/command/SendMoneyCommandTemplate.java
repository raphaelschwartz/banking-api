package com.rschwartz.bankingapi.common.template.command;

import com.rschwartz.bankingapi.accounts.application.port.in.command.SendMoneyCommand;
import java.math.BigDecimal;

public class SendMoneyCommandTemplate {

  public static SendMoneyCommand getValid() {

    return new SendMoneyCommand(
        12345L,
        67890L,
        new BigDecimal(10));
  }

}
