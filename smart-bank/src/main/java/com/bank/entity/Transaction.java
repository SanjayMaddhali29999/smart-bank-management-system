package com.bank.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fromAccount;

    @Column(nullable = false)
    private String toAccount;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String type; 

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    

    public Long getId() { return id; }

    public String getFromAccount() { return fromAccount; }
    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() { return toAccount; }
    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public double getAmount() { return amount; }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() { return type; }
    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
