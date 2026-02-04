package com.bank.service;

import com.bank.repository.CustomerRepository;
import com.bank.repository.LoanRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminDashboardService {

    private final CustomerRepository customerRepository;
    private final LoanRepository loanRepository;

    public AdminDashboardService(CustomerRepository customerRepository,
                                 LoanRepository loanRepository) {
        this.customerRepository = customerRepository;
        this.loanRepository = loanRepository;
    }

    public long totalCustomers() {
        return customerRepository.countAll();
    }

    public long activeCustomers() {
        return customerRepository.countByActive(true);
    }

    public long inactiveCustomers() {
        return customerRepository.countByActive(false);
    }

    public long pendingLoans() {
        return loanRepository.countByStatus("PENDING");
    }

    public long approvedLoans() {
        return loanRepository.countByStatus("APPROVED");
    }
}
