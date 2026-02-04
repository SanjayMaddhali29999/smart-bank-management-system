package com.bank.repository;

import com.bank.entity.Otp;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OtpRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Otp otp) {
        entityManager.persist(otp);
    }

    public Otp findValidOtp(String accountNumber, String otp) {
        try {
            return entityManager.createQuery(
                    "SELECT o FROM Otp o WHERE o.accountNumber = :acc AND o.otp = :otp",
                    Otp.class)
                    .setParameter("acc", accountNumber)
                    .setParameter("otp", otp)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public void delete(Otp otp) {
        entityManager.remove(
                entityManager.contains(otp) ? otp : entityManager.merge(otp));
    }
}
