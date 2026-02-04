package com.bank.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bank.entity.Transaction;
import com.bank.service.AdminCustomerService;
import com.bank.service.AdminDashboardService;
import com.bank.service.AdminLoanService;
import com.bank.service.AdminReportService;
import com.bank.service.CustomerService;

@Controller
public class AdminController {

    private final CustomerService customerService;
    private final AdminLoanService adminLoanService;
    private final AdminCustomerService adminCustomerService;
    private final AdminDashboardService adminDashboardService;
    private final AdminReportService adminReportService;

    public AdminController(CustomerService customerService, AdminLoanService adminLoanService, AdminCustomerService adminCustomerService, AdminDashboardService adminDashboardService, AdminReportService adminReportService)
 {
        this.customerService = customerService;
        this.adminLoanService = adminLoanService;
        this.adminCustomerService = adminCustomerService;
        this.adminDashboardService = adminDashboardService;
        this.adminReportService = adminReportService;
    }

    // Hardcoded admin
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
 
    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin-login";
    }

    @PostMapping("/admin/login")
    public String adminLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        if (username == null || password == null ||
                username.isBlank() || password.isBlank()) {
            model.addAttribute("error", "Username and password are required");
            return "admin-login";
        }

        if (ADMIN_USERNAME.equals(username) &&
            ADMIN_PASSWORD.equals(password)) {

            session.setAttribute("ADMIN_LOGGED_IN", true);
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("error", "Invalid admin credentials");
        return "admin-login";
    }

   
    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {

        Boolean loggedIn =
                (Boolean) session.getAttribute("ADMIN_LOGGED_IN");

        if (loggedIn == null || !loggedIn) {
            return "redirect:/admin/login";
        }

        model.addAttribute("totalCustomers",
                adminDashboardService.totalCustomers());

        model.addAttribute("activeCustomers",
                adminDashboardService.activeCustomers());

        model.addAttribute("inactiveCustomers",
                adminDashboardService.inactiveCustomers());

        model.addAttribute("pendingLoans",
                adminDashboardService.pendingLoans());

        model.addAttribute("approvedLoans",
                adminDashboardService.approvedLoans());

        return "admin-dashboard";
    }

    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }

    @GetMapping("/admin/create-customer")
    public String createCustomerPage(HttpSession session) {

        Boolean loggedIn =
                (Boolean) session.getAttribute("ADMIN_LOGGED_IN");

        if (loggedIn == null || !loggedIn) {
            return "redirect:/admin/login";
        }

        return "admin-create-customer";
    }

    @PostMapping("/admin/create-customer")
    public String createCustomer(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("mobile") String mobile,
            HttpSession session,
            Model model) {

        Boolean loggedIn =
                (Boolean) session.getAttribute("ADMIN_LOGGED_IN");

        if (loggedIn == null || !loggedIn) {
            return "redirect:/admin/login";
        }

        String result =
                customerService.createCustomer(fullName, email, mobile);

        model.addAttribute("message", result);
        return "admin-create-customer";
    }
    
    @GetMapping("/admin/loans")
    public String viewLoans(HttpSession session, Model model) {

        Boolean admin =
                (Boolean) session.getAttribute("ADMIN_LOGGED_IN");

        if (admin == null || !admin)
            return "redirect:/admin/login";

        model.addAttribute(
                "loans",
                adminLoanService.getPendingLoans()
        );

        return "admin-loans";
    }

    @PostMapping("/admin/loan/approve")
    public String approveLoan(
            @RequestParam Long loanId,
            HttpSession session) {

        adminLoanService.approveLoan(loanId);
        return "redirect:/admin/loans";
    }

    @PostMapping("/admin/loan/reject")
    public String rejectLoan(
            @RequestParam Long loanId,
            HttpSession session) {

        adminLoanService.rejectLoan(loanId);
        return "redirect:/admin/loans";
    }
    @GetMapping("/admin/customers")
    public String viewCustomers(HttpSession session, Model model) {

        Boolean loggedIn =
                (Boolean) session.getAttribute("ADMIN_LOGGED_IN");

        if (loggedIn == null || !loggedIn) {
            return "redirect:/admin/login";
        }

        model.addAttribute(
                "customers",
                adminCustomerService.getAllCustomers()
        );

        return "admin-customers";
    }
    @PostMapping("/admin/customer/toggle")
    public String toggleCustomer(
            @RequestParam("customerId") Long customerId,
            HttpSession session,
            Model model) {

        Boolean loggedIn =
                (Boolean) session.getAttribute("ADMIN_LOGGED_IN");

        if (loggedIn == null || !loggedIn) {
            return "redirect:/admin/login";
        }

        String message =
                adminCustomerService.toggleCustomerStatus(customerId);

        model.addAttribute("message", message);

        return "redirect:/admin/customers";
    }
    @GetMapping("/admin/reports")
    public String reportsPage(HttpSession session, Model model) {

        Boolean admin =
                (Boolean) session.getAttribute("ADMIN_LOGGED_IN");

        if (admin == null || !admin)
            return "redirect:/admin/login";

        model.addAttribute(
                "customers",
                adminCustomerService.getAllCustomers()
        );

        return "admin-reports";
    }
    @PostMapping("/admin/reports")
    public String generateReport(
            @RequestParam String reportType,
            @RequestParam(required = false) String month,
            @RequestParam(required = false) Integer year,
            @RequestParam String accountNumber,
            HttpSession session,
            Model model) {

        Boolean admin =
                (Boolean) session.getAttribute("ADMIN_LOGGED_IN");

        if (admin == null || !admin)
            return "redirect:/admin/login";

        List<Transaction> transactions;

        if ("MONTHLY".equals(reportType)) {
            transactions =
                    adminReportService.getMonthlyReport(month, accountNumber);
        } else {
            transactions =
                    adminReportService.getAnnualReport(year, accountNumber);
        }

        
        session.setAttribute("REPORT_TX", transactions);
        session.setAttribute("REPORT_TYPE", reportType);
        session.setAttribute("REPORT_MONTH", month);
        session.setAttribute("REPORT_YEAR", year);
        session.setAttribute("REPORT_ACCOUNT", accountNumber);


        model.addAttribute("transactions", transactions);
        model.addAttribute(
                "customers",
                adminCustomerService.getAllCustomers()
        );

        return "admin-reports";
    }

    
    @GetMapping("/admin/reports/download")
    public String downloadAdminReport(
            HttpSession session,
            HttpServletResponse response,
            Model model) throws Exception {

        Boolean loggedIn = (Boolean) session.getAttribute("ADMIN_LOGGED_IN");
        if (loggedIn == null || !loggedIn) {
            return "redirect:/admin/login";
        }

        String reportType = (String) session.getAttribute("REPORT_TYPE");
        String month      = (String) session.getAttribute("REPORT_MONTH");
        Integer year      = (Integer) session.getAttribute("REPORT_YEAR");
        String account    = (String) session.getAttribute("REPORT_ACCOUNT");

        
        if (reportType == null || reportType.isBlank()) {
            model.addAttribute("error", "No report selected to download");
            return "admin-reports";
        }

        List<Transaction> list;

        if ("MONTHLY".equalsIgnoreCase(reportType)) {
            list = adminReportService.getMonthlyReport(month, account);
        }
        else if ("ANNUAL".equalsIgnoreCase(reportType)) {
            list = adminReportService.getAnnualReport(year, account);
        }
        else {
            model.addAttribute("error", "Invalid report type");
            return "admin-reports";
        }

        if (list == null || list.isEmpty()) {
            model.addAttribute("error", "No transactions found");
            return "admin-reports";
        }

        
        response.setContentType("text/csv");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=admin_report.csv"
        );

        PrintWriter writer = response.getWriter();
        writer.println("Date,Type,From,To,Amount");

        for (Transaction tx : list) {
            writer.println(
                    tx.getTransactionDate() + "," +
                    tx.getType() + "," +
                    tx.getFromAccount() + "," +
                    tx.getToAccount() + "," +
                    tx.getAmount()
            );
        }

        writer.flush();
        return null;
    }



}
