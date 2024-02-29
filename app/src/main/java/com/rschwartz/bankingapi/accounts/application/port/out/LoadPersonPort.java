package com.rschwartz.bankingapi.accounts.application.port.out;

import com.rschwartz.bankingapi.accounts.application.domain.Person;

public interface LoadPersonPort {

  Person findById(Long id);

}
