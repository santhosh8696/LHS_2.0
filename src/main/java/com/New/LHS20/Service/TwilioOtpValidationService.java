package com.New.LHS20.Service;

import javax.servlet.http.HttpSession;

import com.New.LHS20.Controller.ForgotPasswordController;
import com.New.LHS20.Dao.RegistrationRepository;
import com.New.LHS20.Entity.RegistrationForm;
import com.New.LHS20.payload.TwilioOtpValidationPayload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TwilioOtpValidationService {
	@Autowired
	HttpSession session;

	@Autowired
	RegistrationRepository regrepo;

	@Autowired
	ForgotPasswordController passcode;

	@Autowired
	BCryptPasswordEncoder encoder;

	public ResponseEntity<String> reset(TwilioOtpValidationPayload payload) {

		try {
			if (session.getAttribute("otp").equals(payload.getOtp().toString())) {
				String number = String.valueOf(session.getAttribute("to"));

				RegistrationForm regform = regrepo.findByPhoneNo(number);
				System.out.println(regform);
				regform.setPassword(encoder.encode(payload.getPassword().toString()));
				regrepo.save(regform);
				session.invalidate();

				return new ResponseEntity<String>("password changed", HttpStatus.OK);

			} else {
				return new ResponseEntity<String>("wrong otp", HttpStatus.BAD_REQUEST);

			}
		} catch (NullPointerException e) {
			return new ResponseEntity<String>("wrong otp", HttpStatus.BAD_REQUEST);

		}
	}
}
