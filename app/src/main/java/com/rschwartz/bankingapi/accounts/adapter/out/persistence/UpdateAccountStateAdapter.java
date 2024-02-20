package com.rschwartz.bankingapi.accounts.adapter.out.persistence;

import com.rschwartz.bankingapi.accounts.adapter.out.persistence.mapper.ActivityMapper;
import com.rschwartz.bankingapi.accounts.adapter.out.repository.ActivityRepository;
import com.rschwartz.bankingapi.accounts.aplication.port.out.UpdateAccountStatePort;
import com.rschwartz.bankingapi.accounts.domain.AccountNew;
import com.rschwartz.bankingapi.accounts.domain.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
class UpdateAccountStateAdapter implements UpdateAccountStatePort {

  private final ActivityRepository repository;
  private final ActivityMapper mapper;

  @Override
  public void execute(final AccountNew account) {

    log.info("Updating activities: account_id={}", account.getId().get());

    // Improving code
    for (final Activity activity : account.getActivityWindow().getActivities()) {
      if (activity.getId() == null) {
        repository.save(mapper.mapToJpaEntity(activity));
      }
    }

  }

}
