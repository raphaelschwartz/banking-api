package com.rschwartz.bankingapi.accounts.adapter.out.gateway.mapper;

import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.dto.request.NotificationBacenRequest;
import com.rschwartz.bankingapi.accounts.application.domain.Person;
import com.rschwartz.bankingapi.accounts.application.domain.model.Transaction;
import com.rschwartz.bankingapi.common.annotations.Mapper;

@Mapper
public class BacenMapper {

  public NotificationBacenRequest mapDomainToDTO(final Transaction transaction, final Person person) {

    return new NotificationBacenRequest(
        transaction.getKey().getValue(),
        transaction.getCreateDate().getValue(),
        transaction.getType().toString(),
        transaction.getAmount(),
        transaction.getAccountId().getValue(),
        person.getFullName(),
        transaction.getDetail().getValue()
    );
  }

}
