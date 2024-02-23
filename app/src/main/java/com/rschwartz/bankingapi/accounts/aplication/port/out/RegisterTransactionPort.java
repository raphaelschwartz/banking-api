package com.rschwartz.bankingapi.accounts.aplication.port.out;

import com.rschwartz.bankingapi.accounts.aplication.domain.Transaction;

public interface RegisterTransactionPort {

  Transaction save(Transaction transaction);

}
