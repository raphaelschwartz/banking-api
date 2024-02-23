package com.rschwartz.bankingapi.accounts.adapter.out.gateway.client;

import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.dto.request.NotificationBacenRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "bacen-api", url = "https://bacen.com/notifications")
public interface BacenAPIClient {

  @PostMapping
  void notification(@RequestBody NotificationBacenRequest request);

}
