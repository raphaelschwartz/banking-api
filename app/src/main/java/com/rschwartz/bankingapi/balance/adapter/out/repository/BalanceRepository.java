package com.rschwartz.bankingapi.balance.adapter.out.repository;

import com.rschwartz.bankingapi.balance.adapter.out.entity.BalanceJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<BalanceJpaEntity, Long> {

  Optional<BalanceJpaEntity> findByAccountNumber(String accountNumber);

}
