package com.bank.service;

import com.bank.entity.Loan;
import com.bank.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public String applyLoan(String accNo, String loanType, double amount, int tenure) {

        if (amount <= 0)
            return "Invalid loan amount";

        if (tenure <= 0)
            return "Invalid tenure";

        Loan loan = new Loan();
        loan.setAccountNumber(accNo);
        loan.setLoanType(loanType);
        loan.setAmount(amount);
        loan.setTenureMonths(tenure);
        loan.setInterestRate(getInterestRate(loanType));
        loan.setStatus("PENDING");
        loan.setAppliedDate(LocalDate.now());

        loanRepository.save(loan);

        return "Loan applied successfully";
    }

    public List<Loan> getLoans(String accNo) {
        return loanRepository.findByAccountNumber(accNo);
    }

    private double getInterestRate(String loanType) {
        return switch (loanType) {
            case "HOME" -> 8.5;
            case "EDUCATION" -> 6.5;
            default -> 12.0;
        };
    }
}
