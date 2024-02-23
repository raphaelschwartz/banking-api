package com.rschwartz.bankingapi.accounts.integration.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.rschwartz.bankingapi.accounts.adapter.in.web.LoadAccountController;
import com.rschwartz.bankingapi.accounts.adapter.in.web.dto.response.AccountResponse;
import com.rschwartz.bankingapi.accounts.adapter.in.web.mapper.AccountResponseMapper;
import com.rschwartz.bankingapi.accounts.aplication.port.in.useCase.LoadAccountUseCase;
import com.rschwartz.bankingapi.accounts.aplication.port.out.dto.AccountOutput;
import com.rschwartz.bankingapi.common.adapter.in.web.integration.GetMappingControllerTest;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.output.AccountOutputTemplate;
import com.rschwartz.bankingapi.common.template.response.AccountResponseTemplate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = LoadAccountController.class)
public class LoadAccountControllerIntegrationTest extends GetMappingControllerTest {

  private static final String BASE_URI = "/v1/accounts";
  private static final Long ACCOUNT_ID = 12345L;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private LoadAccountUseCase useCase;

  @MockBean
  private AccountResponseMapper mapper;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.OUTPUT.getPath());
    FixtureFactoryLoader.loadTemplates(BaseFixture.RESPONSE.getPath());
  }

  @Test
  @DisplayName("Should get error when account does not exist.")
  void balanceNotFound() throws Exception {

    final String uri = String.format("%s/%s", BASE_URI, ACCOUNT_ID);

    when(useCase.execute(anyLong()))
        .thenReturn(Optional.empty());

    mockMvc
        .perform(get(uri))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$").doesNotExist());
  }

  @Test
  @DisplayName("Should get account by id.")
  void balanceExists() throws Exception {

    final String uri = String.format("%s/%s", BASE_URI, ACCOUNT_ID);

    final AccountOutput output = Fixture.from(AccountOutput.class)
        .gimme(AccountOutputTemplate.VALID);
    when(useCase.execute(anyLong()))
        .thenReturn(Optional.of(output));

    final AccountResponse response = Fixture.from(AccountResponse.class)
        .gimme(AccountResponseTemplate.VALID);
    when(mapper.mapOutputToResponse(any(AccountOutput.class)))
        .thenReturn(response);

    mockMvc
        .perform(get(uri))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("number").exists())
        //.andExpect(jsonPath("accountNumber").value(output.getAccountNumber()))
        .andExpect(jsonPath("balance").exists())
        //.andExpect(jsonPath("value").value(output.getValue()))
        .andExpect(jsonPath("limit").exists())
        //.andExpect(jsonPath("limit").value(output.getLimit()))
        .andExpect(jsonPath("updateDate").exists())
        //.andExpect(jsonPath("updateDate").value(output.getUpdateDate()))
    // FIXME
    ;
  }

}
