package com.rschwartz.bankingapi.accounts.aplication.port.out;

public interface SendMoneyPort {

  void execute(String accountNumber);

}
