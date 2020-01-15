package com.kajes.cimb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kajes.cimb.model.Users;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long>{

}

