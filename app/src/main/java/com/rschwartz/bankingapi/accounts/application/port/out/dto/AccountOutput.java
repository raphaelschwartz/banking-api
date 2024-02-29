package com.rschwartz.bankingapi.accounts.application.port.out.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountOutput {

  private Long id;
  private Long ownerId;
  private String number;
  private BigDecimal balance;
  private LocalDateTime updateDate;

}
