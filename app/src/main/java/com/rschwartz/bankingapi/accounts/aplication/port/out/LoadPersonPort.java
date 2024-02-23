package com.rschwartz.bankingapi.accounts.aplication.port.out;

import com.rschwartz.bankingapi.accounts.aplication.domain.Person;

public interface LoadPersonPort {

  Person findById(Long id);

}
