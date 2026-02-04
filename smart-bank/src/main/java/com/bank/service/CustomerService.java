package com.bank.service;

import com.bank.entity.Customer;
import com.bank.repository.CustomerRepository;
import com.bank.util.AccountUtil;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final EmailService emailService;

    public CustomerService(CustomerRepository customerRepository,
                           EmailService emailService) {
        this.customerRepository = customerRepository;
        this.emailService = emailService;
    }


    public String createCustomer(String fullName, String email, String mobile) {

        
        if (fullName == null || fullName.isBlank())
            return "Full name is required";

        if (email == null || email.isBlank())
            return "Email is required";

        if (mobile == null || mobile.isBlank())
            return "Mobile number is required";

        if (customerRepository.existsByEmail(email))
            return "Email already exists";

        if (customerRepository.existsByMobile(mobile))
            return "Mobile already exists";

        
        Customer customer = new Customer();
        customer.setFullName(fullName);
        customer.setEmail(email);
        customer.setMobile(mobile);

        customer.setAccountNumber(AccountUtil.generateAccountNumber());
        customer.setTempPassword(AccountUtil.generateTempPassword());

        customer.setPassword(""); // not set yet
        customer.setTempPasswordActive(true);
        customer.setActive(true);
        customer.setBalance(0.0);

        customerRepository.save(customer);
        String subject = "Your Smart Bank Account Details";

        String body =
                "Dear " + customer.getFullName() + ",\n\n" +
                "Your Smart Bank account has been created successfully.\n\n" +
                "Account Number: " + customer.getAccountNumber() + "\n" +
                "Temporary Password: " + customer.getTempPassword() + "\n\n" +
                "Please login and reset your password immediately.\n\n" +
                "Regards,\nSmart Bank";

        emailService.sendMail(
                customer.getEmail(),
                subject,
                body
        );

        

        return "SUCCESS : Account created. Account No = "
                + customer.getAccountNumber()
                + " | Temp Password = "
                + customer.getTempPassword();
    }
}
