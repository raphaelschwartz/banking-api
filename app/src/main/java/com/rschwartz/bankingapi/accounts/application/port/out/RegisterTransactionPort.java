package com.rschwartz.bankingapi.accounts.application.port.out;

import com.rschwartz.bankingapi.accounts.application.domain.model.Transaction;

public interface RegisterTransactionPort {

  Transaction save(Transaction transaction);

}
