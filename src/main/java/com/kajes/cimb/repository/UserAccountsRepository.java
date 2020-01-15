package com.kajes.cimb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kajes.cimb.model.UserAccounts;

@Repository
public interface UserAccountsRepository extends JpaRepository<UserAccounts, Long> {

	Optional<UserAccounts> findByAccountNumber(Long accountNumber);

}
