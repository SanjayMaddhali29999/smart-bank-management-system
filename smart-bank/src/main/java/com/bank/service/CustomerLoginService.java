package com.bank.service;

import com.bank.entity.Customer;
import com.bank.repository.CustomerRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

@Service
public class CustomerLoginService {

    private final CustomerRepository customerRepository;

    public CustomerLoginService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Transactional(readOnly = true)
    public String validateLogin(String accountNumber, String password) {

        if (accountNumber == null || accountNumber.isBlank())
            return "Account number required";

        if (password == null || password.isBlank())
            return "Password required";

        Customer customer =
                customerRepository.findByAccountNumber(accountNumber);

        if (customer == null)
            return "Invalid account number";

        
        if (!customer.isActive())
            return "Your account is deactivated. Please contact admin.";

        
        if (customer.isTempPasswordActive()) {

            if (!password.equals(customer.getTempPassword()))
                return "Invalid temporary password";

            return "RESET_REQUIRED";
        }

        
        if (!password.equals(customer.getPassword()))
            return "Invalid password";

        return "SUCCESS";
    }

   
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public String resetPassword(String accountNumber, String newPassword) {

        if (newPassword == null || newPassword.length() < 4)
            return "Password must be at least 4 characters";

        Customer customer =
                customerRepository.findByAccountNumber(accountNumber);

        customer.setPassword(newPassword);
        customer.setTempPasswordActive(false);
        customer.setTempPassword(null);

        entityManager.merge(customer);
        return "SUCCESS";
    }
}
