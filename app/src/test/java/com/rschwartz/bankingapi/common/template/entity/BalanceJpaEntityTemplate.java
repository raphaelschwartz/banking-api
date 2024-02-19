package com.rschwartz.bankingapi.common.template.entity;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.rschwartz.bankingapi.balance.adapter.out.entity.BalanceJpaEntity;
import java.time.LocalDateTime;

public class BalanceJpaEntityTemplate  implements TemplateLoader {

  public static final String VALID = "valid";

  @Override
  public void load() {
    getValidTemplate();
  }

  private static void getValidTemplate() {

    Fixture.of(BalanceJpaEntity.class).addTemplate(VALID, new Rule() {
      {
        add("id", 1L);
        add("accountNumber", "0123456");
        add("value", "10.80");
        add("limit", "100");
        add("updateDate", LocalDateTime.now().minusMinutes(30));
      }
    });

  }

}

