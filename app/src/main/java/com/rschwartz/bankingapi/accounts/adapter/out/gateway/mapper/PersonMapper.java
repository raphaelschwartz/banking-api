package com.rschwartz.bankingapi.accounts.adapter.out.gateway.mapper;

import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.dto.response.UserResponse;
import com.rschwartz.bankingapi.accounts.application.domain.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

  public Person mapDtoToDomain(final UserResponse dto) {

    return Person.restore(
        dto.getId(),
        dto.getFullName());
  }

}
