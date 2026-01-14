package com.example.library.common.repository;

import com.example.library.common.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByAccountIdAndRole(String accountId, String role);

    Optional<UserAccount> findByAccountId(String accountId);

    List<UserAccount> findByRole(String role);

    List<UserAccount> findByRoleNot(String role);

    boolean existsByAccountIdAndRole(String accountId, String role);

    boolean existsByAccountId(String accountId);

    void deleteByAccountIdAndRole(String accountId, String role);

    void deleteByAccountId(String accountId);
}
