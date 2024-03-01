package com.rschwartz.bankingapi.accounts.adapter.out.gateway;

import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.UserClient;
import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.dto.response.UserResponse;
import com.rschwartz.bankingapi.accounts.adapter.out.gateway.mapper.PersonMapper;
import com.rschwartz.bankingapi.accounts.application.domain.Person;
import com.rschwartz.bankingapi.accounts.application.port.out.LoadPersonPort;
import com.rschwartz.bankingapi.common.adapter.out.exception.ExternalDependencyException;
import com.rschwartz.bankingapi.common.annotations.ClientAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ClientAdapter
@RequiredArgsConstructor
@Slf4j
public class LoadPersonClientAdapter implements LoadPersonPort {

  private final UserClient apiClient;
  private final PersonMapper mapper;

  @Override
  public Person findById(final Long id) {

    log.info("Loading person: person_id={}", id);

    final UserResponse dto = getPerson(id);

    return mapper.mapDtoToDomain(dto);
  }

  private UserResponse getPerson(final Long id) {

    try {
      return apiClient.findById(id);
    } catch (final Exception ex) {
      log.error("Error when calling the users API: method={} message={}", "getPerson", ex.getMessage());
      throw new ExternalDependencyException();
    }
  }

}
