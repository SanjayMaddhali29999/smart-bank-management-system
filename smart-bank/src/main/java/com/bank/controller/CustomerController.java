package com.bank.controller;

import com.bank.entity.Customer;
import com.bank.entity.Transaction;
import com.bank.repository.LoanRepository;
import com.bank.service.CustomerDashboardService;
import com.bank.service.CustomerLoginService;
import com.bank.service.FundTransferService;
import com.bank.service.LoanService;
import com.bank.service.OtpService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController {

    private final CustomerLoginService customerLoginService;
    private final OtpService otpService;
    
    private final CustomerDashboardService dashboardService;
    private final FundTransferService fundTransferService;
    private final LoanService loanService;
    private final LoanRepository loanRepository;

    public CustomerController(CustomerLoginService customerLoginService,
                              OtpService otpService,
                              CustomerDashboardService dashboardService, FundTransferService fundTransferService, LoanService loanService, LoanRepository loanRepository) {
        this.customerLoginService = customerLoginService;
        this.otpService = otpService;
        this.dashboardService = dashboardService;
        this.fundTransferService = fundTransferService;
        this.loanService = loanService;
        this.loanRepository = loanRepository;
    }


    

    @GetMapping("/customer/login")
    public String customerLoginPage() {
        return "customer-login";
    }

    @PostMapping("/customer/login")
    public String customerLogin(
            @RequestParam("accountNumber") String accountNumber,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        String result =
                customerLoginService.validateLogin(accountNumber, password);

        if ("RESET_REQUIRED".equals(result)) {
            session.setAttribute("RESET_ACC", accountNumber);
            return "redirect:/customer/reset-password";
        }

        if ("SUCCESS".equals(result)) {
            session.setAttribute("OTP_ACC", accountNumber);
            otpService.generateOtp(accountNumber);
            return "redirect:/customer/otp";
        }

        model.addAttribute("error", result);
        return "customer-login";
    }
    
    @GetMapping("/customer/reset-password")
    public String resetPage() {
        return "reset-password";
    }

    @PostMapping("/customer/reset-password")
    public String resetPassword(
            @RequestParam("newPassword") String newPassword,
            HttpSession session,
            Model model) {

        String accountNumber =
                (String) session.getAttribute("RESET_ACC");

        String result =
                customerLoginService.resetPassword(accountNumber, newPassword);

        if ("SUCCESS".equals(result)) {
            session.removeAttribute("RESET_ACC");
            return "redirect:/customer/login";
        }

        model.addAttribute("error", result);
        return "reset-password";
    }
    @GetMapping("/customer/otp")
    public String otpPage() {
        return "customer-otp";
    }

    @PostMapping("/customer/otp")
    public String verifyOtp(
            @RequestParam("otp") String otp,
            HttpSession session,
            Model model) {

        String accountNumber =
                (String) session.getAttribute("OTP_ACC");

        if (accountNumber == null) {
            return "redirect:/customer/login";
        }

        boolean valid =
                otpService.validateOtp(accountNumber, otp);

        if (!valid) {
            model.addAttribute("error", "Invalid or expired OTP");
            return "customer-otp";
        }

        session.removeAttribute("OTP_ACC");
        session.setAttribute("CUSTOMER_LOGGED_IN", true);
        session.setAttribute("LOGGED_IN_ACC", accountNumber);
        return "redirect:/customer/dashboard";

    }
    @GetMapping("/customer/dashboard")
    public String customerDashboard(HttpSession session, Model model) {

        Boolean loggedIn =
                (Boolean) session.getAttribute("CUSTOMER_LOGGED_IN");

        if (loggedIn == null || !loggedIn) {
            return "redirect:/customer/login";
        }

        String accountNumber =
                (String) session.getAttribute("LOGGED_IN_ACC");

        Customer customer =
                dashboardService.getCustomerByAccount(accountNumber);

        if (customer == null) {
            return "redirect:/customer/login";
        }

        model.addAttribute("customer", customer);
        return "customer-dashboard";
    }
    
    @GetMapping("/customer/transfer")
    public String transferPage(HttpSession session) {

        Boolean loggedIn =
                (Boolean) session.getAttribute("CUSTOMER_LOGGED_IN");

        if (loggedIn == null || !loggedIn) {
            return "redirect:/customer/login";
        }

        return "fund-transfer";
    }

    @PostMapping("/customer/transfer")
    public String fundTransfer(
            @RequestParam("toAccount") String toAccount,
            @RequestParam("amount") double amount,
            HttpSession session,
            Model model) {

        Boolean loggedIn =
                (Boolean) session.getAttribute("CUSTOMER_LOGGED_IN");

        if (loggedIn == null || !loggedIn) {
            return "redirect:/customer/login";
        }

        String fromAccount =
                (String) session.getAttribute("LOGGED_IN_ACC");

        String result =
                fundTransferService.transfer(fromAccount, toAccount, amount);

        model.addAttribute("message", result);

        return "fund-transfer"; 
    }

    
    @GetMapping("/customer/statement")
    public String miniStatement(HttpSession session, Model model) {

        Boolean loggedIn =
                (Boolean) session.getAttribute("CUSTOMER_LOGGED_IN");

        if (loggedIn == null || !loggedIn) {
            return "redirect:/customer/login";
        }

        String accNo =
                (String) session.getAttribute("LOGGED_IN_ACC");

        List<Transaction> transactions =
                dashboardService.getMiniStatement(accNo);

        
        for (Transaction tx : transactions) {
            if (accNo.equals(tx.getFromAccount())) {
                tx.setType("DEBIT");
            } else {
                tx.setType("CREDIT");
            }
        }

        model.addAttribute("transactions", transactions);

        return "customer-statement";
    }
    
    @GetMapping("/customer/logout")
    public String logout(HttpSession session) {

        if (session != null) {
            session.invalidate(); 
        }

        return "redirect:/customer/login";
    }

    @GetMapping("/customer/statement/download")
    public String downloadStatement(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            HttpSession session,
            HttpServletResponse response,
            Model model) throws Exception {

        
        Boolean loggedIn =
                (Boolean) session.getAttribute("CUSTOMER_LOGGED_IN");

        if (loggedIn == null || !loggedIn) {
            return "redirect:/customer/login";
        }

        String accNo =
                (String) session.getAttribute("LOGGED_IN_ACC");

        if (accNo == null) {
            return "redirect:/customer/login";
        }

        
        List<Transaction> list;

        if (year != null) {
            
            list = dashboardService.getFilteredStatement(accNo, month, year);
        } else {
            
            list = dashboardService.getMiniStatement(accNo);
        }

        if (list == null || list.isEmpty()) {
            model.addAttribute("transactions", list);
            model.addAttribute("error", "No transactions to download");
            return "customer-statement";
        }

        
        response.setContentType("text/csv");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=transaction_statement.csv"
        );

        PrintWriter writer = response.getWriter();
        writer.println("Date,Type,From,To,Amount");

        for (Transaction tx : list) {

            
            String displayType;
            if (accNo.equals(tx.getFromAccount())) {
                displayType = "DEBIT";
            } else {
                displayType = "CREDIT";
            }

            writer.println(
                    tx.getTransactionDate() + "," +
                    displayType + "," +
                    tx.getFromAccount() + "," +
                    tx.getToAccount() + "," +
                    tx.getAmount()
            );
        }

        writer.flush();
        return null; 
    }


    @GetMapping("/customer/statement/filter")
    public String filterStatement(
            @RequestParam(required = false) Integer month,
            @RequestParam Integer year,
            HttpSession session,
            Model model) {

        Boolean loggedIn =
                (Boolean) session.getAttribute("CUSTOMER_LOGGED_IN");

        if (loggedIn == null || !loggedIn)
            return "redirect:/customer/login";

        String accNo =
                (String) session.getAttribute("LOGGED_IN_ACC");

        List<Transaction> list =
                dashboardService.getFilteredStatement(accNo, month, year);

        model.addAttribute("transactions", list);
        model.addAttribute("month", month);
        model.addAttribute("year", year);

        return "customer-statement";
    }

    @GetMapping("/customer/loan/apply")
    public String applyLoanPage(HttpSession session) {

        Boolean loggedIn =
                (Boolean) session.getAttribute("CUSTOMER_LOGGED_IN");

        if (loggedIn == null || !loggedIn) {
            return "redirect:/customer/login";
        }

        return "customer-loan-apply";
    }

    @PostMapping("/customer/loan/apply")
    public String applyLoan(
            @RequestParam String loanType,
            @RequestParam double amount,
            @RequestParam int tenure,
            HttpSession session,
            Model model) {

        String accNo =
                (String) session.getAttribute("LOGGED_IN_ACC");

        String result =
                loanService.applyLoan(accNo, loanType, amount, tenure);

        model.addAttribute("message", result);
        return "customer-loan-apply";
    }
    
    @GetMapping("/customer/loans")
    public String myLoans(HttpSession session, Model model) {

        String accNo = (String) session.getAttribute("LOGGED_IN_ACC");

        model.addAttribute(
                "loans",
                loanRepository.findByAccountNumber(accNo)
        );

        return "customer-loan";
    }


}
