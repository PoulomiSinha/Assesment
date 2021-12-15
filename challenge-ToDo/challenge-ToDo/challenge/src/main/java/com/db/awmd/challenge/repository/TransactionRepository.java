package com.db.awmd.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.db.awmd.challenge.domain.TransactionInfo;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionInfo, Long>{

}
