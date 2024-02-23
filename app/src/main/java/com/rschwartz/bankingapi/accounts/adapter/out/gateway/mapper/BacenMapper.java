package com.rschwartz.bankingapi.accounts.adapter.out.gateway.mapper;

import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.dto.request.NotificationBacenRequest;
import com.rschwartz.bankingapi.accounts.aplication.domain.Person;
import com.rschwartz.bankingapi.accounts.aplication.domain.Transaction;
import org.springframework.stereotype.Component;

@Component
public class BacenMapper {

  public NotificationBacenRequest mapDomainToDTO(final Transaction transaction, final Person person) {

    return new NotificationBacenRequest(
        transaction.getTransferId(),
        transaction.getCreateDate(),
        transaction.getType(),
        transaction.getAmount(),
        transaction.getAccountId(),
        person.getFullName(),
        transaction.getDetail()
    );
  }

}
