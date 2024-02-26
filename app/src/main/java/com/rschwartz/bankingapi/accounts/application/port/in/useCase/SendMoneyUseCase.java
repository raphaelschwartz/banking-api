package com.rschwartz.bankingapi.accounts.application.port.in.useCase;

import com.rschwartz.bankingapi.accounts.application.port.in.useCase.dto.SendMoneyInput;

public interface SendMoneyUseCase {

  void execute(SendMoneyInput input);

}
