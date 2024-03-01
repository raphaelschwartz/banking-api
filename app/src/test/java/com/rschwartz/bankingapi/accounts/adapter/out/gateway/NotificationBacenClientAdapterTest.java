package com.rschwartz.bankingapi.accounts.adapter.out.gateway;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.BacenClient;
import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.dto.request.NotificationBacenRequest;
import com.rschwartz.bankingapi.accounts.adapter.out.gateway.mapper.BacenMapper;
import com.rschwartz.bankingapi.accounts.application.domain.Person;
import com.rschwartz.bankingapi.accounts.application.domain.model.Transaction;
import com.rschwartz.bankingapi.common.adapter.out.exception.ExternalDependencyException;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.domain.PersonTemplate;
import com.rschwartz.bankingapi.common.template.domain.TransactionTemplate;
import com.rschwartz.bankingapi.common.template.request.NotificationBacenRequestTemplate;
import feign.FeignException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificationBacenClientAdapterTest {

  @InjectMocks
  private NotificationBacenClientAdapter adapter;
  @Mock
  private BacenMapper mapper;

  @Mock
  private BacenClient apiClient;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.REQUEST.getPath());
  }

  @Test
  @DisplayName("Should get error when API fails.")
  void clientError() {

    final NotificationBacenRequest request = Fixture.from(NotificationBacenRequest.class)
        .gimme(NotificationBacenRequestTemplate.VALID);
    when(mapper.mapDomainToDTO(any(Transaction.class), any(Person.class)))
        .thenReturn(request);

    doThrow(FeignException.class)
        .when(apiClient).notification(any(NotificationBacenRequest.class));

    final Transaction transaction = TransactionTemplate.getValidRestoreWithdrawTransferTemplate();
    final Person person = PersonTemplate.getValidTemplate();

    assertThrows(ExternalDependencyException.class,
        () -> adapter.send(transaction, person));

    verify(mapper, times(1))
        .mapDomainToDTO(any(Transaction.class), any(Person.class));

    verify(apiClient, times(1))
        .notification(any(NotificationBacenRequest.class));
  }

  @Test
  @DisplayName("Should send notification to BACEN.")
  void sendNotification() {

    final NotificationBacenRequest request = Fixture.from(NotificationBacenRequest.class)
        .gimme(NotificationBacenRequestTemplate.VALID);
    when(mapper.mapDomainToDTO(any(Transaction.class), any(Person.class)))
        .thenReturn(request);

    final Transaction transaction = TransactionTemplate.getValidRestoreWithdrawTransferTemplate();
    final Person person = PersonTemplate.getValidTemplate();

    assertDoesNotThrow(() -> adapter.send(transaction, person));

    verify(mapper, times(1))
        .mapDomainToDTO(any(Transaction.class), any(Person.class));

    verify(apiClient, times(1))
        .notification(any(NotificationBacenRequest.class));
  }

}
