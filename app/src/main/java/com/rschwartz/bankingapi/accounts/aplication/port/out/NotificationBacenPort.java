package com.rschwartz.bankingapi.accounts.aplication.port.out;

import com.rschwartz.bankingapi.accounts.aplication.domain.Person;
import com.rschwartz.bankingapi.accounts.aplication.domain.Transaction;

public interface NotificationBacenPort {

  void send(Transaction transaction, Person person);

}
