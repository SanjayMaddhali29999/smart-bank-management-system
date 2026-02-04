package com.bank.service;

import com.bank.entity.Customer;
import com.bank.entity.Loan;
import com.bank.entity.Transaction;
import com.bank.repository.CustomerRepository;
import com.bank.repository.LoanRepository;
import com.bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminLoanService {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public AdminLoanService(LoanRepository loanRepository,
                            CustomerRepository customerRepository,
                            TransactionRepository transactionRepository) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<Loan> getPendingLoans() {
        return loanRepository.findByStatus("PENDING");
    }

    @Transactional
    public String approveLoan(Long loanId) {

        Loan loan = loanRepository.findById(loanId);

        if (loan == null)
            return "Loan not found";

        if (!"PENDING".equals(loan.getStatus()))
            return "Loan already processed";

        Customer customer =
                customerRepository.findByAccountNumber(
                        loan.getAccountNumber()
                );

        if (customer == null)
            return "Customer not found";

       
        if (!customer.isActive())
            return "Customer account is deactivated. Loan cannot be approved";

        
        customer.setBalance(
                customer.getBalance() + loan.getAmount()
        );

        
        loan.setStatus("APPROVED");

        customerRepository.save(customer);
        loanRepository.save(loan);

       
        Transaction tx = new Transaction();
        tx.setFromAccount("BANK");          
        tx.setToAccount(customer.getAccountNumber());
        tx.setAmount(loan.getAmount());
        tx.setType("LOAN_CREDIT");
        tx.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(tx);

        return "Loan approved successfully";
    }


    public String rejectLoan(Long loanId) {

    	Loan loan = loanRepository.findById(loanId);


        if (loan == null)
            return "Loan not found";

        loan.setStatus("REJECTED");
        loanRepository.save(loan);

        return "Loan rejected";
    }
}
