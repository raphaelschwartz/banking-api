package com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.dto.request;

import com.rschwartz.bankingapi.accounts.application.domain.model.Money;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NotificationBacenRequest {

  private UUID key;
  private LocalDateTime createDate;
  private String type;
  private Money amount;
  private Long accountId;
  private String fullName;
  private String detail;

}
