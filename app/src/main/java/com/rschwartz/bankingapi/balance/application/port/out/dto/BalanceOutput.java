package com.rschwartz.bankingapi.balance.application.port.out.dto;

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
public class BalanceOutput {

  private String accountNumber;

  private BigDecimal value;

  private String limit;

  private LocalDateTime updateDate;

}
