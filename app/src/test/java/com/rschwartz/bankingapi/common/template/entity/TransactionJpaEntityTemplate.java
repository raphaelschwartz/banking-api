package com.rschwartz.bankingapi.common.template.entity;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.entity.TransactionJpaEntity;
import com.rschwartz.bankingapi.accounts.application.domain.model.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionJpaEntityTemplate implements TemplateLoader {

  public static final String VALID_WITHDRAW = "valid_withdraw";

  @Override
  public void load() {
    getValidTemplate();
  }

  private static void getValidTemplate() {

    Fixture.of(TransactionJpaEntity.class).addTemplate(VALID_WITHDRAW, new Rule() {
      {
        add("id", 1L);
        add("type", TransactionType.WITHDRAW.toString());
        add("detail", "Trânsferência enviada para 67890");
        add("createDate", LocalDateTime.now());
        add("amount", new BigDecimal("10.80"));
        add("accountId", 50L);
        add("balanceAfter", new BigDecimal("100.80"));
        add("key", UUID.randomUUID());
      }

    });

  }

}
