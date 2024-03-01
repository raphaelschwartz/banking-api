package com.rschwartz.bankingapi.accounts.adapter.out.gateway;

import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.BacenClient;
import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.dto.request.NotificationBacenRequest;
import com.rschwartz.bankingapi.accounts.adapter.out.gateway.mapper.BacenMapper;
import com.rschwartz.bankingapi.accounts.application.domain.Person;
import com.rschwartz.bankingapi.accounts.application.domain.model.Transaction;
import com.rschwartz.bankingapi.accounts.application.port.out.NotificationBacenPort;
import com.rschwartz.bankingapi.common.adapter.out.exception.ExternalDependencyException;
import com.rschwartz.bankingapi.common.annotations.ClientAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ClientAdapter
@RequiredArgsConstructor
@Slf4j
public class NotificationBacenClientAdapter implements NotificationBacenPort {

  private final BacenMapper mapper;
  private final BacenClient apiClient;

  @Override
  public void send(final Transaction transaction, final Person person) {

    log.info("Notification BACEN: transaction_id={}", transaction.getKey());

    notify(mapper.mapDomainToDTO(transaction, person));
  }

  private void notify(final NotificationBacenRequest request) {

    try {
      apiClient.notification(request);
    } catch (final Exception ex) {
      // TODO Add retry or DLQ if the problem persists.
      log.error("Error when calling the BACEN: method={} message={}", "notify", ex.getMessage());
      throw new ExternalDependencyException();
    }
  }

}
