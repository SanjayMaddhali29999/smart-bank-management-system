package com.bank.repository;

import com.bank.entity.Loan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class LoanRepository {

    @PersistenceContext
    private EntityManager entityManager;

   
    @Transactional
    public void save(Loan loan) {
        if (loan.getId() == null) {
            entityManager.persist(loan);
        } else {
            entityManager.merge(loan);
        }
    }

    
    public Loan findById(Long id) {
        return entityManager.find(Loan.class, id);
    }

    
    public List<Loan> findByAccountNumber(String accountNumber) {
        return entityManager.createQuery(
                "SELECT l FROM Loan l WHERE l.accountNumber = :acc",
                Loan.class)
                .setParameter("acc", accountNumber)
                .getResultList();
    }

    
    public List<Loan> findByStatus(String status) {
        return entityManager.createQuery(
                "SELECT l FROM Loan l WHERE l.status = :status",
                Loan.class)
                .setParameter("status", status)
                .getResultList();
    }

    
    public long countByStatus(String status) {
        return entityManager.createQuery(
                "SELECT COUNT(l) FROM Loan l WHERE l.status = :status",
                Long.class)
                .setParameter("status", status)
                .getSingleResult();
    }
}
