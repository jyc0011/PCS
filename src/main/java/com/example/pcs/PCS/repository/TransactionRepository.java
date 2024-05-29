package com.example.pcs.PCS.repository;

import com.example.pcs.PCS.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
