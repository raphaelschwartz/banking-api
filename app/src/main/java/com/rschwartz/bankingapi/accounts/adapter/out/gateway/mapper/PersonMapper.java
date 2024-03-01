package com.rschwartz.bankingapi.accounts.adapter.out.gateway.mapper;

import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.dto.response.UserResponse;
import com.rschwartz.bankingapi.accounts.application.domain.Person;
import com.rschwartz.bankingapi.common.annotations.Mapper;

@Mapper
public class PersonMapper {

  public Person mapDtoToDomain(final UserResponse dto) {

    return Person.restore(
        dto.getId(),
        dto.getFullName());
  }

}
