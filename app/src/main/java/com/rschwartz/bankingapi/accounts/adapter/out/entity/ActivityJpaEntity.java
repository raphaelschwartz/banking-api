package com.rschwartz.bankingapi.accounts.adapter.out.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "activity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityJpaEntity {

  @Id
  @GeneratedValue
  private Long id;

  private UUID externalId;

  private String type;

  private LocalDateTime createDate;

  private BigDecimal amount;

  private Long sourceAccountId;

  private BigDecimal sourceAccountBalance;

  private Long targetAccountId;

}
