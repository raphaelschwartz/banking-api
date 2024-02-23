package com.rschwartz.bankingapi.accounts.adapter.out.gateway;

import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.UserAPIClient;
import com.rschwartz.bankingapi.accounts.adapter.out.gateway.mapper.PersonMapper;
import com.rschwartz.bankingapi.accounts.aplication.domain.Person;
import com.rschwartz.bankingapi.accounts.aplication.port.out.LoadPersonPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoadPersonClientAdapter implements LoadPersonPort {

  private final UserAPIClient apiClient;
  private final PersonMapper mapper;

  @Override
  public Person findById(final Long id) {

    log.info("Loading person: person_id={}", id);

    return mapper.mapDTOToDomain(apiClient.findById(id));
  }

}
