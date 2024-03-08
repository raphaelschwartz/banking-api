package com.rschwartz.bankingapi.accounts.application.port.in.useCase;

import com.rschwartz.bankingapi.accounts.application.port.in.command.SendMoneyCommand;

public interface SendMoneyUseCase {

  void execute(SendMoneyCommand command);

}
