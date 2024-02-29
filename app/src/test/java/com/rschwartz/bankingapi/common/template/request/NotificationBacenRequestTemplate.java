package com.rschwartz.bankingapi.common.template.request;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.dto.request.NotificationBacenRequest;
import com.rschwartz.bankingapi.accounts.application.domain.model.Money;
import com.rschwartz.bankingapi.accounts.application.domain.model.TransactionType;
import java.time.LocalDateTime;
import java.util.UUID;

public class NotificationBacenRequestTemplate implements TemplateLoader {

  public static final String VALID = "valid";
  public static final String INVALID_REQUIRED = "invalid_required";

  @Override
  public void load() {
    getValidTemplate();
    getInvalidTemplate();
  }

  private static void getValidTemplate() {

    Fixture.of(NotificationBacenRequest.class).addTemplate(VALID, new Rule() {
      {
        add("key", UUID.randomUUID());
        add("createDate", LocalDateTime.now().minusSeconds(2));
        add("type", TransactionType.WITHDRAW.toString());
        add("amount", Money.ofInteger(100));
        add("accountId", 123L);
        add("fullName", "Name Fake do Teste");
        add("detail", "Trânsferência enviada para 67890");
      }

    });
  }

  private static void getInvalidTemplate() {

    Fixture.of(NotificationBacenRequest.class).addTemplate(INVALID_REQUIRED)
        .inherits(VALID, new Rule() {
          {
            add("key", null);
          }
        });

  }

}
