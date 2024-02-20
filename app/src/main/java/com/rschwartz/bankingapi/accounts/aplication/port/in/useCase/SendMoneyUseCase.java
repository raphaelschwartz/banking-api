package com.rschwartz.bankingapi.accounts.aplication.port.in.useCase;

import com.rschwartz.bankingapi.accounts.aplication.port.in.useCase.dto.SendMoneyInput;

public interface SendMoneyUseCase {

  boolean execute(SendMoneyInput input);

}
