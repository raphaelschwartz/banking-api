package com.rschwartz.bankingapi.accounts.adapter.out.persistence.repository;

import com.rschwartz.bankingapi.accounts.adapter.out.persistence.entity.TransactionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionJpaEntity, Long> {

}
