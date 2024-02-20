package com.rschwartz.bankingapi.accounts.integration.adapter.in.web;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.rschwartz.bankingapi.accounts.adapter.in.web.LoadAccountController;
import com.rschwartz.bankingapi.accounts.aplication.port.in.useCase.LoadAccountUseCase;
import com.rschwartz.bankingapi.accounts.aplication.port.out.dto.AccountOutput;
import com.rschwartz.bankingapi.common.adapter.in.web.integration.GetMappingControllerTest;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.output.AccountOutputTemplate;
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

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private LoadAccountUseCase useCase;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.OUTPUT.getPath());
  }

  @Test
  @DisplayName("Should get error when balance does not exist.")
  void balanceNotFound() throws Exception {

    final String uri = String.format("%s/%s", BASE_URI, 1L);

    when(useCase.execute(anyString()))
        .thenReturn(Optional.empty());

    mockMvc
        .perform(get(uri))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$").doesNotExist());
  }

  @Test
  @DisplayName("Should get balance by account number.")
  void balanceExists() throws Exception {

    final String accountNumber = "0123456789";
    final String uri = String.format("%s/%s", BASE_URI, accountNumber);

    final AccountOutput output = Fixture.from(AccountOutput.class)
        .gimme(AccountOutputTemplate.VALID);

    when(useCase.execute(anyString()))
        .thenReturn(Optional.of(output));

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
