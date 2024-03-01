package com.rschwartz.bankingapi.common.template.request;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.rschwartz.bankingapi.accounts.adapter.in.web.dto.request.SendMoneyRequest;

public class SendMoneyRequestTemplate implements TemplateLoader {

  public static final String VALID = "valid";
  public static final String INVALID_REQUIRED = "invalid_required";

  @Override
  public void load() {
    getValidTemplate();
    getInvalidTemplate();
  }

  private static void getValidTemplate() {

    Fixture.of(SendMoneyRequest.class).addTemplate(VALID, new Rule() {
      {
        add("sourceAccountId", 1L);
        add("targetAccountId", 2L);
        add("amount", "50.00");
      }
    });
  }

  private static void getInvalidTemplate() {

    Fixture.of(SendMoneyRequest.class).addTemplate(INVALID_REQUIRED, new Rule() {
      {
        add("sourceAccountId", null);
        add("targetAccountId", null);
        add("amount", null);
      }
    });

  }

}
