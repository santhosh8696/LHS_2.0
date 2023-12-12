package com.New.LHS20.Service;

import java.util.Random;

import javax.servlet.http.HttpSession;

import com.New.LHS20.Dao.RegistrationRepository;
import com.New.LHS20.payload.ForgotPasswordTwilio;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwilioServ {

	@Autowired
	HttpSession session;

	@Autowired
	RegistrationRepository regrepo;

	private String sid = "ACc7ecfde0c58529372a987618c4eecdd2";
	private String token = "661f6139624c6a8b4c951bc6a9bd1474";
	private String trialnumber = "+14783758910";

	public void send(ForgotPasswordTwilio twilio) {

		if (true && regrepo.existsByPhoneNo(twilio.getPhoneNo())) {
			Random r = new Random();
			int i = r.nextInt(9999);
			if (i < 1000) {
				i += 1000;
			}

			String msg = "your otp is " + i;
			Twilio.init(sid, token);
			MessageCreator message = Message.creator(new PhoneNumber(twilio.getPhoneNo()), new PhoneNumber(trialnumber),
					msg);
			message.create();
			String otp = String.valueOf(i);
			this.session.setAttribute("otp", otp);
			this.session.setAttribute("to", twilio.getPhoneNo());
			System.out.println("sent");
		}
	}

}
