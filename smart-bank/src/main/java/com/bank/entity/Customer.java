package com.bank.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String mobile;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private String password;        

    @Column(nullable = true)
    private String tempPassword;   

    @Column(nullable = false)
    private boolean tempPasswordActive;

    @Column(nullable = false)
    private boolean active;
    
    @Column(nullable = false)
    private double balance = 0.0;

    

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTempPassword() {
        return tempPassword;
    }

    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }

    public boolean isTempPasswordActive() {
        return tempPasswordActive;
    }

    public void setTempPasswordActive(boolean tempPasswordActive) {
        this.tempPasswordActive = tempPasswordActive;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

}
