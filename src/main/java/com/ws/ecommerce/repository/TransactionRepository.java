package com.ws.ecommerce.repository;

import com.ws.ecommerce.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByBuyersId (Long buyersId);
    List<Transaction> findByItemName (String itemName);
}
