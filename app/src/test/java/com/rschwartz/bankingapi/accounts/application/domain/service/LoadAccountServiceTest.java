package com.rschwartz.bankingapi.accounts.application.domain.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.rschwartz.bankingapi.accounts.application.domain.model.Account;
import com.rschwartz.bankingapi.accounts.application.port.out.LoadAccountPort;
import com.rschwartz.bankingapi.accounts.application.port.out.dto.AccountOutput;
import com.rschwartz.bankingapi.accounts.application.port.out.mapper.AccountOutputMapper;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.domain.AccountTemplate;
import com.rschwartz.bankingapi.common.template.output.AccountOutputTemplate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoadAccountServiceTest {

  private static final Long ACCOUNT_ID = 12345L;

  @InjectMocks
  private LoadAccountService service;

  @Mock
  private LoadAccountPort port;

  @Mock
  private AccountOutputMapper mapper;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.OUTPUT.getPath());
  }

  @Test
  @DisplayName("Should get optional empty when account does not exist.")
  void accountNotFound() {

    when(port.findById(anyLong()))
        .thenReturn(Optional.empty());

    final Optional<AccountOutput> result = service.execute(ACCOUNT_ID);

    assertAll(() -> {
      assertNotNull(result);
      assertFalse(result.isPresent());
    });

    verify(port, times(1))
        .findById(anyLong());

    verify(mapper, never())
        .mapDomainToOutput(any(Account.class));
  }

  @Test
  @DisplayName("Should get account by id.")
  void accountExists() {

    final Account account = AccountTemplate.getTemplateOne();
    when(port.findById(anyLong()))
        .thenReturn(Optional.of(account));

    final AccountOutput output = Fixture.from(AccountOutput.class)
        .gimme(AccountOutputTemplate.VALID);
    when(mapper.mapDomainToOutput(any(Account.class)))
        .thenReturn(output);

    final Optional<AccountOutput> result = service.execute(ACCOUNT_ID);

    assertAll(() -> {
      assertNotNull(result);
      assertTrue(result.isPresent());
    });

    verify(port, times(1))
        .findById(anyLong());

    verify(mapper, times(1))
        .mapDomainToOutput(any(Account.class));
  }

}
