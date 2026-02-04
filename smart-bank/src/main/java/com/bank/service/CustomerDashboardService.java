package com.bank.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.entity.Customer;
import com.bank.entity.Transaction;
import com.bank.repository.CustomerRepository;
import com.bank.repository.TransactionRepository;

@Service
public class CustomerDashboardService {

    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public CustomerDashboardService(CustomerRepository customerRepository,
                                    TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    public Customer getCustomerByAccount(String accNo) {
        return customerRepository.findByAccountNumber(accNo);
    }

    public List<Transaction> getMiniStatement(String accNo) {
        List<Transaction> list =
                transactionRepository.findRecentTransactions(accNo);

        
        return list.size() > 10 ? list.subList(0, 10) : list;
    }
    public List<Transaction> getFilteredStatement(
            String accNo,
            Integer month,
            Integer year) {

        LocalDateTime start;
        LocalDateTime end;

        if (month != null) {
            start = LocalDate.of(year, month, 1).atStartOfDay();
            end = start.plusMonths(1).minusSeconds(1);
        } else {
            start = LocalDate.of(year, 1, 1).atStartOfDay();
            end = LocalDate.of(year, 12, 31).atTime(23, 59, 59);
        }

        List<Transaction> list =
                transactionRepository.findByDateRange(accNo, start, end);

        
        for (Transaction tx : list) {
            if (accNo.equals(tx.getFromAccount())) {
                tx.setType("DEBIT");
            } else {
                tx.setType("CREDIT");
            }
        }

        return list;
    }
    public List<Transaction> getMonthlyReport(String accNo, String month) {

        YearMonth ym = YearMonth.parse(month); // yyyy-MM

        LocalDateTime start = ym.atDay(1).atStartOfDay();
        LocalDateTime end = ym.atEndOfMonth().atTime(23, 59, 59);

        return transactionRepository
                .findByAccountAndDateRange(accNo, start, end);
    }

    public List<Transaction> getAnnualReport(String accNo, int year) {

        LocalDateTime start =
                Year.of(year).atDay(1).atStartOfDay();

        LocalDateTime end =
                Year.of(year).atMonth(12).atEndOfMonth()
                    .atTime(23, 59, 59);

        return transactionRepository
                .findByAccountAndDateRange(accNo, start, end);
    }

}

