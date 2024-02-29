package com.rschwartz.bankingapi.accounts.adapter.out.gateway;

import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.BacenClient;
import com.rschwartz.bankingapi.accounts.adapter.out.gateway.mapper.BacenMapper;
import com.rschwartz.bankingapi.accounts.application.domain.Person;
import com.rschwartz.bankingapi.accounts.application.domain.model.Transaction;
import com.rschwartz.bankingapi.accounts.application.port.out.NotificationBacenPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationBacenClientAdapter implements NotificationBacenPort {

  private final BacenMapper mapper;
  private final BacenClient apiClient;

  @Override
  public void send(final Transaction transaction, final Person person) {

    log.info("Notification BACEN: transaction_id={}", transaction.getKey());

    apiClient.notification(
        mapper.mapDomainToDTO(transaction, person));
  }

}
