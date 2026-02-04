package com.bank.repository;

import com.bank.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;
 
    @Transactional
    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            entityManager.persist(customer);
            return customer;
        }
        return entityManager.merge(customer);
    }


   
    public Customer findById(Long id) {
        return entityManager.find(Customer.class, id);
    }

    
    public List<Customer> findAll() {
        return entityManager.createQuery(
                "SELECT c FROM Customer c",
                Customer.class
        ).getResultList();
    }

    public boolean existsByEmail(String email) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(c) FROM Customer c WHERE c.email = :email",
                Long.class)
                .setParameter("email", email)
                .getSingleResult();

        return count > 0;
    }

    public boolean existsByMobile(String mobile) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(c) FROM Customer c WHERE c.mobile = :mobile",
                Long.class)
                .setParameter("mobile", mobile)
                .getSingleResult();

        return count > 0;
    }
    public Customer findByAccountNumber(String accountNumber) {
        try {
            return entityManager.createQuery(
                    "SELECT c FROM Customer c WHERE c.accountNumber = :acc",
                    Customer.class)
                    .setParameter("acc", accountNumber)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    public long countAll() {
        return entityManager.createQuery(
                "SELECT COUNT(c) FROM Customer c", Long.class)
                .getSingleResult();
    }

    public long countByActive(boolean active) {
        return entityManager.createQuery(
                "SELECT COUNT(c) FROM Customer c WHERE c.active = :active",
                Long.class)
                .setParameter("active", active)
                .getSingleResult();
    }


}
