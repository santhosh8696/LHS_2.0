package com.New.LHS20.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.New.LHS20.Service.MailService;
import com.New.LHS20.Service.OtpValidationService;
import com.New.LHS20.payload.ForgotPassword;
import com.New.LHS20.payload.OtpValidationPayload;

@RestController
@RequestMapping("/api")
public class ForgotPasswordController {

	@Autowired
	OtpValidationService otpvalid;

	@Autowired
	MailService mailService;

	@PostMapping("/email")
	public ResponseEntity checkEmail(@RequestBody ForgotPassword forgotPassword) {
		ResponseEntity res = mailService.forgotPassword(forgotPassword);
     return new ResponseEntity("otp sent",HttpStatus.ACCEPTED);

	}

	@PostMapping("/validate")
	public ResponseEntity validateEmail(@RequestBody OtpValidationPayload otpValidationPayload) {
		ResponseEntity res = otpvalid.reset(otpValidationPayload);
		return new ResponseEntity("password Reset",HttpStatus.ACCEPTED);
	}
}
