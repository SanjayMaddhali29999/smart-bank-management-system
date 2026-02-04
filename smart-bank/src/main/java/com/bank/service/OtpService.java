package com.bank.service;

import com.bank.entity.Customer;
import com.bank.entity.Otp;
import com.bank.repository.CustomerRepository;
import com.bank.repository.OtpRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    private final OtpRepository otpRepository;
    private final Random random = new Random();
    private final EmailService emailService;
    private final CustomerRepository customerRepository;

    public OtpService(OtpRepository otpRepository,
                      CustomerRepository customerRepository,
                      EmailService emailService) {

        this.otpRepository = otpRepository;
        this.customerRepository = customerRepository;
        this.emailService = emailService;
    }


    @Transactional
    public void generateOtp(String accountNumber) {

        String otp = String.valueOf(
                (int)(Math.random() * 900000) + 100000);

        Otp entity = new Otp();
        entity.setAccountNumber(accountNumber);
        entity.setOtp(otp);
        entity.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        otpRepository.save(entity);

        
        Customer customer =
                customerRepository.findByAccountNumber(accountNumber);

        if (customer == null) {
            return; 
        }

        String subject = "Smart Bank OTP Verification";
        String body =
                "Dear " + customer.getFullName() + ",\n\n" +
                "Your OTP is: " + otp + "\n" +
                "Valid for 5 minutes.\n\n" +
                "Regards,\nSmart Bank";

        emailService.sendMail(
                customer.getEmail(),
                subject,
                body
        );
    }



    public boolean validateOtp(String accountNumber, String otpInput) {

        Otp otp = otpRepository.findValidOtp(accountNumber, otpInput);

        if (otp == null)
            return false;

        if (otp.getExpiryTime().isBefore(LocalDateTime.now()))
            return false;

        otpRepository.delete(otp);
        return true;
    }
}
