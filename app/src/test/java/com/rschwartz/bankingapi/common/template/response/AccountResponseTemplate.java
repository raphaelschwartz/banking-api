package com.rschwartz.bankingapi.common.template.response;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.rschwartz.bankingapi.accounts.adapter.in.web.dto.response.AccountResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountResponseTemplate implements TemplateLoader {

  public static final String VALID = "valid";

  @Override
  public void load() {
    getValidTemplate();
  }

  private static void getValidTemplate() {

    Fixture.of(AccountResponse.class).addTemplate(VALID, new Rule() {
      {
        add("id", 1L);
        add("ownerId", 1234L);
        add("number", "0123456");
        add("balance", new BigDecimal("10.80"));
        add("updateDate", LocalDateTime.now().minusMinutes(30));
      }
    });

  }

}
