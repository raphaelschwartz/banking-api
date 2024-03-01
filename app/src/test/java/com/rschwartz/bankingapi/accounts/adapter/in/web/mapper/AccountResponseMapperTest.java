package com.rschwartz.bankingapi.accounts.adapter.in.web.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.rschwartz.bankingapi.accounts.adapter.in.web.dto.response.AccountResponse;
import com.rschwartz.bankingapi.accounts.application.port.out.dto.AccountOutput;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.output.AccountOutputTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountResponseMapperTest {

  @InjectMocks
  private AccountResponseMapper mapper;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.OUTPUT.getPath());
  }

  @Test
  @DisplayName("Should convert output to response.")
  void mapOutputToResponse() {

    final AccountOutput output = Fixture.from(AccountOutput.class)
        .gimme(AccountOutputTemplate.VALID);

    final AccountResponse result = mapper.mapOutputToResponse(output);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(output.getId(), result.getId());
      assertEquals(output.getOwnerId(), result.getOwnerId());
      assertEquals(output.getNumber(), result.getNumber());
      assertEquals(output.getBalance(), result.getBalance());
      assertEquals(output.getUpdateDate(), result.getUpdateDate());
    });
  }

}
