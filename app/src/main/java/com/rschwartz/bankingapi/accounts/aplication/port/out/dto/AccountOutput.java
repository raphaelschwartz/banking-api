package com.rschwartz.bankingapi.accounts.aplication.port.out.dto;

import java.math.BigDecimal;
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
public class AccountOutput {

  private UUID externalId;
  private UUID ownerId;
  private String accountNumber;
  private BigDecimal value;
  private BigDecimal limit;
  private LocalDateTime updateDate;

}
