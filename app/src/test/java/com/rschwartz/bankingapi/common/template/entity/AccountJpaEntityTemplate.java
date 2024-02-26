package com.rschwartz.bankingapi.common.template.entity;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.entity.AccountJpaEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountJpaEntityTemplate implements TemplateLoader {

  public static final String VALID = "valid";

  @Override
  public void load() {
    getValidTemplate();
  }

  private static void getValidTemplate() {

    Fixture.of(AccountJpaEntity.class).addTemplate(VALID, new Rule() {
      {
        add("id", 1L);
        add("number", "0123456");
        add("status", "ACTIVE");
        add("ownerId", 1234L);
        add("balance", new BigDecimal("10.80"));
        add("updateDate", LocalDateTime.now().minusMinutes(30));
        add("active", Boolean.TRUE);
      }

    });

  }

}
