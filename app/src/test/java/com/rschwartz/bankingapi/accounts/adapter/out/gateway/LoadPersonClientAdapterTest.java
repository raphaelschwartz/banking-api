package com.rschwartz.bankingapi.accounts.adapter.out.gateway;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.UserClient;
import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.dto.response.UserResponse;
import com.rschwartz.bankingapi.accounts.adapter.out.gateway.mapper.PersonMapper;
import com.rschwartz.bankingapi.accounts.application.domain.Person;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.client.UserResponseTemplate;
import com.rschwartz.bankingapi.common.template.domain.PersonTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoadPersonClientAdapterTest {

  @InjectMocks
  private LoadPersonClientAdapter adapter;
  @Mock
  private UserClient apiClient;

  @Mock
  private PersonMapper mapper;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.CLIENT.getPath());
  }

  @Test
  @DisplayName("Should get person by id.")
  void findById() {

    final UserResponse response = Fixture.from(UserResponse.class)
        .gimme(UserResponseTemplate.VALID);
    when(apiClient.findById(anyLong()))
        .thenReturn(response);

    final Person domain = PersonTemplate.getValidTemplate();
    when(mapper.mapDtoToDomain(any(UserResponse.class)))
        .thenReturn(domain);

    final Person result = adapter.findById(1L);
    assertNotNull(result);

    verify(apiClient, times(1))
        .findById(anyLong());

    verify(mapper, times(1))
        .mapDtoToDomain(any(UserResponse.class));
  }

}
