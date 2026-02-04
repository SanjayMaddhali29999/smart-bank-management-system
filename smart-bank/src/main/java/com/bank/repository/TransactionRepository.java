package com.bank.repository;

import com.bank.entity.Transaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TransactionRepository {

    @PersistenceContext
    private EntityManager entityManager;

 
    
    @Transactional
    public void save(Transaction tx) {
        entityManager.persist(tx);
    }

    public List<Transaction> findRecentTransactions(String acc) {
        return entityManager.createQuery("""
            SELECT t FROM Transaction t
            WHERE t.fromAccount = :acc OR t.toAccount = :acc
            ORDER BY t.transactionDate DESC
        """, Transaction.class)
        .setParameter("acc", acc)
        .getResultList();
    }


    public List<Transaction> findByDateRange(
            LocalDateTime start,
            LocalDateTime end
    ) {
        return entityManager.createQuery("""
            SELECT t FROM Transaction t
            WHERE t.transactionDate BETWEEN :start AND :end
            ORDER BY t.transactionDate DESC
        """, Transaction.class)
        .setParameter("start", start)
        .setParameter("end", end)
        .getResultList();
    }
    public List<Transaction> findByDateRange(
            String acc,
            LocalDateTime start,
            LocalDateTime end
    ) {
        return entityManager.createQuery("""
            SELECT t FROM Transaction t
            WHERE (t.fromAccount = :acc OR t.toAccount = :acc)
              AND t.transactionDate BETWEEN :start AND :end
            ORDER BY t.transactionDate DESC
        """, Transaction.class)
        .setParameter("acc", acc)
        .setParameter("start", start)
        .setParameter("end", end)
        .getResultList();
    }


    public List<Transaction> findByAccountAndDateRange(
            String acc,
            LocalDateTime start,
            LocalDateTime end
    ) {
        return entityManager.createQuery("""
            SELECT t FROM Transaction t
            WHERE (t.fromAccount = :acc OR t.toAccount = :acc)
            AND t.transactionDate BETWEEN :start AND :end
            ORDER BY t.transactionDate DESC
        """, Transaction.class)
        .setParameter("acc", acc)
        .setParameter("start", start)
        .setParameter("end", end)
        .getResultList();
    }
}
