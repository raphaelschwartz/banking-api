package com.rschwartz.bankingapi.accounts.adapter.out.gateway;

import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.BacenAPIClient;
import com.rschwartz.bankingapi.accounts.adapter.out.gateway.mapper.BacenMapper;
import com.rschwartz.bankingapi.accounts.aplication.domain.Person;
import com.rschwartz.bankingapi.accounts.aplication.domain.Transaction;
import com.rschwartz.bankingapi.accounts.aplication.port.out.NotificationBacenPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationBacenClientAdapter implements NotificationBacenPort {

  private final BacenAPIClient apiClient;
  private final BacenMapper mapper;

  @Override
  public void send(final Transaction transaction, final Person person) {

    log.info("Notification bacen: transaction_id={}", transaction.getTransferId());

    apiClient.notification(
        mapper.mapDomainToDTO(transaction, person));
  }

}
