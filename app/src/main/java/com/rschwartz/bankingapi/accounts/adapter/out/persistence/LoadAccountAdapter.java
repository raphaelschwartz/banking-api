package com.rschwartz.bankingapi.accounts.adapter.out.persistence;

import com.rschwartz.bankingapi.accounts.adapter.out.entity.AccountJpaEntity;
import com.rschwartz.bankingapi.accounts.adapter.out.entity.ActivityJpaEntity;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.mapper.AccountMapper;
import com.rschwartz.bankingapi.accounts.adapter.out.repository.AccountRepository;
import com.rschwartz.bankingapi.accounts.adapter.out.repository.ActivityRepository;
import com.rschwartz.bankingapi.accounts.aplication.port.out.LoadAccountPortError;
import com.rschwartz.bankingapi.accounts.domain.AccountNew;
import com.rschwartz.bankingapi.accounts.domain.AccountNew.AccountId;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoadAccountAdapter implements LoadAccountPortError {

  private final AccountRepository accountRepository;
  private final ActivityRepository activityRepository;
  private final AccountMapper accountMapper;

  @Override
  public AccountNew execute(final AccountId accountId, final LocalDateTime baselineDate) {

    log.info("Loading account: account_id={} baseline_date={}", accountId, baselineDate);

    final AccountJpaEntity account =
        //accountRepository.findById(accountId.getValue())
        accountRepository.findById(1L)

            .orElseThrow(EntityNotFoundException::new);

    final List<ActivityJpaEntity> activities =
        activityRepository.findByOwnerSince(
            1L, //accountId.getValue(),
            baselineDate);

    final Long withdrawalBalance = activityRepository
        .getWithdrawalBalanceUntil(
            1L, //accountId.getValue(),
            baselineDate)
        .orElse(0L);

    final Long depositBalance = activityRepository
        .getDepositBalanceUntil(
            1L, //accountId.getValue(),
            baselineDate)
        .orElse(0L);

    return accountMapper.mapToDomain(
        account,
        activities,
        withdrawalBalance,
        depositBalance);
  }

  /*private Account convert(final TransferJpaEntity entity) {

    return Account.restore(
        entity.getId(),
        entity.getAccountNumber(),
        entity.getValue(),
        entity.getLimit(),
        entity.getUpdateDate());
  }

   */

}
