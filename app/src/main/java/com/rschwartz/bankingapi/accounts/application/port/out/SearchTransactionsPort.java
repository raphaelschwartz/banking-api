package com.rschwartz.bankingapi.accounts.application.port.out;

import com.rschwartz.bankingapi.accounts.application.domain.model.AccountId;
import com.rschwartz.bankingapi.accounts.application.domain.model.Transaction;
import java.time.LocalDateTime;
import java.util.List;

public interface SearchTransactionsPort {

  List<Transaction> search(AccountId id, LocalDateTime baselineDate);

}
