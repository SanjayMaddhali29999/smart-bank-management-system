package com.bank.service;

import com.bank.entity.Customer;
import com.bank.entity.Transaction;
import com.bank.repository.CustomerRepository;
import com.bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FundTransferService {

    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public FundTransferService(CustomerRepository customerRepository,
                               TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public String transfer(String fromAcc, String toAcc, double amount) {
    	
    	if (fromAcc == null || toAcc == null)
            return "Invalid account";
    	
        if (fromAcc.equals(toAcc))
            return "Cannot transfer to same account";

        if (amount <= 0)
            return "Invalid transfer amount";

        Customer sender =
                customerRepository.findByAccountNumber(fromAcc);

        Customer receiver =
                customerRepository.findByAccountNumber(toAcc);
        
        if (sender == null)
            return "Sender account not found";
        
        if (receiver == null)
            return "Receiver account not found";
        
        if (!sender.isActive())
            return "Your account is deactivated. Transfer not allowed";

        if (!receiver.isActive())
            return "Receiver account is deactivated. Transfer not allowed";
        
        if (sender.getBalance() < amount)
            return "Insufficient balance";

        
        sender.setBalance(sender.getBalance() - amount);

        
        receiver.setBalance(receiver.getBalance() + amount);

        customerRepository.save(sender);
        customerRepository.save(receiver);

        
        Transaction tx = new Transaction();
        tx.setFromAccount(fromAcc);
        tx.setToAccount(toAcc);
        tx.setAmount(amount);
        tx.setType("TRANSFER");
        tx.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(tx);

        return "SUCCESS";
    }

    public List<Transaction> getTransactions(String account) {
        return transactionRepository.findRecentTransactions(account);
    }
    
}

