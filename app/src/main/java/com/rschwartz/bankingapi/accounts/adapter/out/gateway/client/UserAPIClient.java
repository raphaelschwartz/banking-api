package com.rschwartz.bankingapi.accounts.adapter.out.gateway.client;

import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.dto.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "users-api", url = "https://internal-banking.com/users")
public interface UserAPIClient {

  @GetMapping(value = "/{id}")
  UserResponse findById(@PathVariable Long id);

}
