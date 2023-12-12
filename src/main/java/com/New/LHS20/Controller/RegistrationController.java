package com.New.LHS20.Controller;

import javax.validation.Valid;

//import com.New.LHS20.Dto.JwtRequestPayload;
//import com.New.LHS20.Dto.JwtResponse;
import com.New.LHS20.Dto.RegistrationDto;
import com.New.LHS20.Entity.Patient;
import com.New.LHS20.Entity.RegistrationForm;
import com.New.LHS20.Service.RegistrationService;
import com.New.LHS20.Service.TwilioOtpValidationService;
import com.New.LHS20.Service.TwilioServ;
import com.New.LHS20.payload.ForgotPasswordTwilio;
import com.New.LHS20.payload.TwilioOtpValidationPayload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RegistrationController {

	@Autowired
	private RegistrationService regservice;

	@Autowired
	private TwilioServ tservice;

	@Autowired
	private TwilioOtpValidationService totpservice;
	
	
	
    //User can register
	@PostMapping("/register")
	public Patient add(@RequestBody @Valid RegistrationDto dto) {

		

		return regservice.addUser(dto);

	}

	
	//To receive mobile otp
	@PostMapping("/sendotp")
	public ResponseEntity sendotp(@RequestBody ForgotPasswordTwilio twilio) {

		tservice.send(twilio);
		return new ResponseEntity("Otp sent successfully", HttpStatus.OK);

	}

	
	//Mobile otp  validation
	@PostMapping("/validateotp")
	public ResponseEntity validateEmail(@RequestBody TwilioOtpValidationPayload otpValidationPayload) {
		return totpservice.reset(otpValidationPayload);
	}

}
