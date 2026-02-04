package com.bank.service;

import com.bank.entity.Customer;
import com.bank.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminCustomerService {

    private final CustomerRepository customerRepository;

    public AdminCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    
    @Transactional
    public String toggleCustomerStatus(Long customerId) {

        Customer customer = customerRepository.findById(customerId);

        if (customer == null) {
            return "Customer not found";
        }

        customer.setActive(!customer.isActive());
        customerRepository.save(customer);

        return customer.isActive()
                ? "Customer activated successfully"
                : "Customer deactivated successfully";
    }
}
