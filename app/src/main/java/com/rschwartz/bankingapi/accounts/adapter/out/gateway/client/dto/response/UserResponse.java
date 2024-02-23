package com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class UserResponse {

  private final Long id;
  private final String fullName;

}
