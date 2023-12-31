package com.New.LHS20.Service;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.New.LHS20.Controller.ForgotPasswordController;
import com.New.LHS20.Dao.RegistrationRepository;
import com.New.LHS20.Entity.RegistrationForm;
import com.New.LHS20.payload.OtpValidationPayload;

@Service
public class OtpValidationService {
    @Autowired
    HttpSession session;

    @Autowired
    RegistrationRepository repo;

    @Autowired
    ForgotPasswordController passcode;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    RegistrationForm regform;

    @Autowired
    MailService mailService;

    public ResponseEntity reset(OtpValidationPayload payload) {


            String otp= String.valueOf(mailService.i);
            String to=mailService.to;
            session.setAttribute("otp",otp);
            session.setAttribute("to",to);
            if (session.getAttribute("otp").equals(payload.getOtp().toString())) {
            String email = String.valueOf(session.getAttribute("to"));
            RegistrationForm regform = repo.findByEmail(email);
            regform.setPassword(encoder.encode(payload.getPassword().toString()));
            RegistrationForm regform1=repo.save(regform);

            return new ResponseEntity<String>("Reset", HttpStatus.OK);
        }

        else {
            System.out.println("else called");
            return new ResponseEntity<String>("Wrong otp", HttpStatus.BAD_REQUEST);
        }

    }
}