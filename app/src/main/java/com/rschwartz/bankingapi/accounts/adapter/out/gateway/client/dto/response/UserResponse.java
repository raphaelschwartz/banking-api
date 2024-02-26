package com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

  private Long id;
  private String fullName;

}
