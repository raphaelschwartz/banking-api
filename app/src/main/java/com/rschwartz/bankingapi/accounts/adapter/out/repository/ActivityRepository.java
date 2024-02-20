package com.rschwartz.bankingapi.accounts.adapter.out.repository;

import com.rschwartz.bankingapi.accounts.adapter.out.entity.ActivityJpaEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ActivityRepository extends JpaRepository<ActivityJpaEntity, Long> {

  @Query("""
			select activity from ActivityJpaEntity activity
			where activity.sourceAccountId = :ownerAccountId
			and activity.createDate >= :since
			""")
  List<ActivityJpaEntity> findByOwnerSince(
      @Param("ownerAccountId") long ownerAccountId,
      @Param("since") LocalDateTime since);

  @Query("""
			select sum(activity.amount) from ActivityJpaEntity activity
			where activity.sourceAccountId = :accountId
			and activity.createDate < :until
			""")
  Optional<Long> getWithdrawalBalanceUntil(
      @Param("accountId") long accountId,
      @Param("until") LocalDateTime until);

  @Query("""
			select sum(activity.amount) from ActivityJpaEntity activity
			where activity.targetAccountId = :accountId
			and activity.createDate < :until
			""")
  Optional<Long> getDepositBalanceUntil(
      @Param("accountId") long accountId,
      @Param("until") LocalDateTime until);

}
