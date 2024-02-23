package com.rschwartz.bankingapi.accounts.adapter.out.gateway.mapper;

import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.dto.response.UserResponse;
import com.rschwartz.bankingapi.accounts.aplication.domain.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

  public Person mapDTOToDomain(final UserResponse dto) {

    return Person.restore(
        dto.getId(),
        dto.getFullName());
  }

}
