package com.New.LHS20.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.New.LHS20.Dao.PatientRepository;
import com.New.LHS20.Dao.RegistrationRepository;
import com.New.LHS20.Dao.SlotRepo;
import com.New.LHS20.Dto.RegistrationDto;
import com.New.LHS20.Entity.Authorities;
import com.New.LHS20.Entity.Patient;
import com.New.LHS20.Entity.RegistrationForm;
import com.New.LHS20.Exception.RegistrationCustomException;
import com.New.LHS20.Exception.ResourceAlreadyExistsException;

@Service
public class RegistrationService {


	@Autowired
	private RegistrationRepository registrationRepo;

	@Autowired
	private PatientRepository patientrepo;
	
	@Autowired
	private SlotRepo slotRepo;

	// adding the user
	public Patient addUser(RegistrationDto dto) {
		RegistrationForm regform = new RegistrationForm();

		ModelMapper modelmapper = new ModelMapper();
		if (dto == null) {
			throw new RuntimeException("null found in registration plss check");
		} else if (registrationRepo.existsByUsername(dto.getUsername())) {
			throw new RegistrationCustomException("707", "Username Already Exists please enter different One");
		} else if (registrationRepo.existsByPhoneNo(dto.getPhoneNo())) {
			throw new ResourceAlreadyExistsException(
					"Phone number already exists please register with different phone number");
		} else

		{
			modelmapper.map(dto, regform);

			BCryptPasswordEncoder bcryptpasswordencoder = new BCryptPasswordEncoder();
			regform.setPassword(bcryptpasswordencoder.encode(regform.getPassword()));
			Authorities authority = new Authorities();
			Patient patient = new Patient();

			authority.setRoleName("USER");
			regform.setRoleName(authority.getRoleName());

			regform.getRoles().add(authority);

			registrationRepo.save(regform);
			ModelMapper modelmapper1 = new ModelMapper();
			modelmapper1.map(regform, patient);

			return patientrepo.save(patient);

		}

	}

}
