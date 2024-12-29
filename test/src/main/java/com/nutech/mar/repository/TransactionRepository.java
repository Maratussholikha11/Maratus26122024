package com.nutech.mar.repository;

import com.nutech.mar.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(value = "SELECT * FROM tb_transaction WHERE user_id = :id ORDER BY created_on DESC", nativeQuery = true)
    List<Transaction> getAll(Integer id);

    @Query(value = "SELECT * FROM tb_transaction ORDER BY created_on DESC LIMIT ?1 OFFSET ?2", nativeQuery = true)
    List<Transaction> getAll(int a, int b, Integer id);
}
