package com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.dto.request;

import com.rschwartz.bankingapi.accounts.aplication.domain.Money;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class NotificationBacenRequest {

  private final UUID key;
  private final LocalDateTime createDate;
  private String type;
  private final Money amount;
  private final Long accountId;
  private final String fullName;
  private final String detail;

}
