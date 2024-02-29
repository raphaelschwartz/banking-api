package com.rschwartz.bankingapi.accounts.adapter.out.gateway.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.dto.response.UserResponse;
import com.rschwartz.bankingapi.accounts.application.domain.Person;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.client.UserResponseTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PersonMapperTest {

  @InjectMocks
  private PersonMapper mapper;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.CLIENT.getPath());
  }

  @Test
  @DisplayName("Should convert DTO to domain.")
  void mapDtoToDomain() {

    final UserResponse dto = Fixture.from(UserResponse.class)
        .gimme(UserResponseTemplate.VALID);

    final Person result = mapper.mapDtoToDomain(dto);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(dto.getId(), result.getId());
      assertEquals(dto.getFullName(), result.getFullName());
    });
  }

}
