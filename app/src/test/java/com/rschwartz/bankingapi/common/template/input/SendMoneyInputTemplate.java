package com.rschwartz.bankingapi.common.template.input;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.rschwartz.bankingapi.accounts.application.domain.model.Money;
import com.rschwartz.bankingapi.accounts.application.port.in.useCase.dto.SendMoneyInput;

public class SendMoneyInputTemplate implements TemplateLoader {

  public static final String VALID = "valid";

  @Override
  public void load() {
    getValidTemplate();
  }

  private static void getValidTemplate() {

    Fixture.of(SendMoneyInput.class).addTemplate(VALID, new Rule() {
      {
        {
          add("sourceAccountId", 12345L);
          add("targetAccountId", 67890L);
          add("amount", Money.ofInteger(10));
        }
      }
    });

  }

}
