package com.rschwartz.bankingapi.accounts.application.port.out;

import com.rschwartz.bankingapi.accounts.application.domain.Person;
import com.rschwartz.bankingapi.accounts.application.domain.model.Transaction;

public interface NotificationBacenPort {

  void send(Transaction transaction, Person person);

}
