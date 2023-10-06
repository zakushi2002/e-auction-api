package com.e.auction.api.repository;

import com.e.auction.api.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    Account findAccountByEmail(String email);
    Account findAccountByResetPwdCode(String resetPwdCode);
}
