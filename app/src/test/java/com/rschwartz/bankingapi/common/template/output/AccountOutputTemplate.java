package com.rschwartz.bankingapi.common.template.output;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.rschwartz.bankingapi.accounts.aplication.port.out.dto.AccountOutput;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountOutputTemplate implements TemplateLoader {

  public static final String VALID = "valid";

  @Override
  public void load() {
    getValidTemplate();
  }

  private static void getValidTemplate() {

    Fixture.of(AccountOutput.class).addTemplate(VALID, new Rule() {
      {
        add("accountNumber", "0123456");
        add("value", new BigDecimal("10.80"));
        add("limit", new BigDecimal("100.80"));
        add("updateDate", LocalDateTime.now().minusMinutes(30));
      }
    });

  }

}
