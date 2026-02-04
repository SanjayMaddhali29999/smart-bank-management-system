package com.bank.service;

import com.bank.entity.Transaction;
import com.bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class AdminReportService {

    private final TransactionRepository transactionRepository;

    public AdminReportService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    public List<Transaction> getMonthlyReport(
            String month,
            String accountNumber
    ) {

        YearMonth ym = YearMonth.parse(month);

        LocalDateTime start = ym.atDay(1).atStartOfDay();
        LocalDateTime end   = ym.atEndOfMonth().atTime(23, 59, 59);

        if (accountNumber != null && !accountNumber.isBlank()) {
            return transactionRepository.findByAccountAndDateRange(accountNumber, start, end);
        }
        return transactionRepository.findByDateRange(start, end);

    }

    
    public List<Transaction> getAnnualReport(
            int year,
            String accountNumber // null or blank → ALL customers
    ) {

        LocalDateTime start =
                LocalDateTime.of(year, 1, 1, 0, 0);

        LocalDateTime end =
                LocalDateTime.of(year, 12, 31, 23, 59, 59);

        
        if (accountNumber == null || accountNumber.isBlank()) {
            return transactionRepository.findByDateRange(start, end);
        }

       
        return transactionRepository
                .findByAccountAndDateRange(accountNumber, start, end);
    }
}
