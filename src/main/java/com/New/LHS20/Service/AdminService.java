package com.New.LHS20.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.New.LHS20.Dao.AdminRepository;
import com.New.LHS20.Dao.DoctorRepository;
import com.New.LHS20.Dao.LocationRepository;
import com.New.LHS20.Dao.NurseRepository;
import com.New.LHS20.Dao.RegistrationRepository;
import com.New.LHS20.Entity.Authorities;
import com.New.LHS20.Entity.Doctor;
import com.New.LHS20.Entity.Location;
import com.New.LHS20.Entity.Nurse;
import com.New.LHS20.Entity.RegistrationForm;
import com.New.LHS20.Exception.RegistrationCustomException;
import com.New.LHS20.Exception.ResourceAlreadyExistsException;

@Service
public class AdminService {

	 
	@Autowired
	private DoctorRepository doctorrepo;

	@Autowired
	private NurseRepository nurserepo;
	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	private RegistrationRepository regrepo;
	
	@Autowired
	private AdminRepository adminrepo;

	@Autowired
	private LocationRepository locationRepository;
	
	// Adding Admin
	public String addAdmin(RegistrationForm regform) {

		System.out.println("Add admin method called");

		if (regform == null) {
			throw new RuntimeException("null found in registration plss check");
		} else if (regrepo.existsByUsername(regform.getUsername())) {
			throw new RegistrationCustomException("707", "Username Already Exists please enter different One");
		}else if (regrepo.existsByPhoneNo(regform.getPhoneNo())) {
            System.out.println(regrepo.existsByPhoneNo(regform.getPhoneNo()));
            throw new ResourceAlreadyExistsException("Phone number already exists please register with different phone number");
        } else

		{
			BCryptPasswordEncoder bcryptpasswordencoder = new BCryptPasswordEncoder();
			regform.setPassword(bcryptpasswordencoder.encode(regform.getPassword()));

			Authorities authority = new Authorities();

			authority.setRoleName("ADMIN");
			regform.setRoleName(authority.getRoleName());

			regform.getRoles().add(authority);

			regrepo.save(regform);

			return "saved";

		}
	}

	// Adding Doctor
	// @PreAuthorize("hasAuthority('ADMIN')")
	public Doctor addDoctor(RegistrationForm regform) {
		System.out.println("Hello");

		if (regform == null) {
			throw new RuntimeException("null found in registration plss check");
		} else if (regrepo.existsByUsername(regform.getUsername())) {
			throw new RegistrationCustomException("707", "Username Already Exists please enter different One");
		} else if (regrepo.existsByPhoneNo(regform.getPhoneNo())) {
            System.out.println(regrepo.existsByPhoneNo(regform.getPhoneNo()));
            throw new ResourceAlreadyExistsException("Phone number already exists please register with different phone number");
        }else

		{
			BCryptPasswordEncoder bcryptpasswordencoder = new BCryptPasswordEncoder();
			regform.setPassword(bcryptpasswordencoder.encode(regform.getPassword()));
			Authorities authority = new Authorities();
			Doctor doctor = new Doctor();

			authority.setRoleName("DOCTOR");
			regform.setRoleName(authority.getRoleName());

			regform.getRoles().add(authority);

			regrepo.save(regform);
			ModelMapper modelmapper = new ModelMapper();
			modelmapper.map(regform, doctor);

			return doctorrepo.save(doctor);

		}

	}

	// Adding Nurse
	public Nurse addNurse(RegistrationForm regform) {

		if (regform == null) {
			throw new RuntimeException("null found in registration plss check");
		} else if (regrepo.existsByUsername(regform.getUsername())) {
			throw new RegistrationCustomException("707", "Username Already Exists please enter different One");
		}else if (regrepo.existsByPhoneNo(regform.getPhoneNo())) {
            System.out.println(regrepo.existsByPhoneNo(regform.getPhoneNo()));
            throw new ResourceAlreadyExistsException("Phone number already exists please register with different phone number");
        } else

		{
			BCryptPasswordEncoder bcryptpasswordencoder = new BCryptPasswordEncoder();
			regform.setPassword(bcryptpasswordencoder.encode(regform.getPassword()));
			Authorities authority = new Authorities();
			Nurse nurse = new Nurse();
			authority.setRoleName("NURSE");
			regform.setRoleName(authority.getRoleName());

			regform.getRoles().add(authority);
			regrepo.save(regform);
			ModelMapper modelmapper = new ModelMapper();
			modelmapper.map(regform, nurse);

			return nurserepo.save(nurse);

		}
	}

	// Adding Pharmacist
	public String addPharmacist(RegistrationForm regform) {

		if (regform == null) {
			throw new RuntimeException("null found in registration plss check");
		} else if (regrepo.existsByUsername(regform.getUsername())) {
			throw new RegistrationCustomException("707", "Username Already Exists please enter different One");
		} else if (regrepo.existsByPhoneNo(regform.getPhoneNo())) {
            System.out.println(regrepo.existsByPhoneNo(regform.getPhoneNo()));
            throw new ResourceAlreadyExistsException("Phone number already exists please register with different phone number");
        }else

		{
			BCryptPasswordEncoder bcryptpasswordencoder = new BCryptPasswordEncoder();
			regform.setPassword(bcryptpasswordencoder.encode(regform.getPassword()));
			Authorities authority = new Authorities();

			authority.setRoleName("PHARMACIST");

			regform.setRoleName(authority.getRoleName());

			regform.getRoles().add(authority);

			regrepo.save(regform);
			return "saved";
		}
	}

	// adding Receptionist
	public String addReceptionist(RegistrationForm regform) {
		if (regform == null) {
			throw new RuntimeException("null found in registration plss check");
		} else if (regrepo.existsByUsername(regform.getUsername())) {
			throw new RegistrationCustomException("707", "Username Already Exists please enter different One");
		} else if (regrepo.existsByPhoneNo(regform.getPhoneNo())) {
            System.out.println(regrepo.existsByPhoneNo(regform.getPhoneNo()));
            throw new ResourceAlreadyExistsException("Phone number already exists please register with different phone number");
        }else {
			BCryptPasswordEncoder bcryptpasswordencoder = new BCryptPasswordEncoder();
			regform.setPassword(bcryptpasswordencoder.encode(regform.getPassword()));
			Authorities authority = new Authorities();
			authority.setRoleName("RECEPTIONIST");
			regform.setRoleName(authority.getRoleName());
			regform.getRoles().add(authority);
			regrepo.save(regform);
			return "saved";
		}
	}

	//     @PreAuthorize("hasAuthority('ADMIN')")
	public List<RegistrationForm> getRole(String rolename) {
		return regrepo.findByRoleName(rolename);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	public String test() {
		return "Hello";
	}

// update details
	public RegistrationForm updateDetails(RegistrationForm regform) {
		if (regrepo.existsByUsername(regform.getUsername())) {
			Doctor doctor = doctorrepo.findById(regform.getUserId());
			doctor.setFirstName(regform.getFirstName());
			doctor.setLastName(regform.getLastName());
			doctor.setId(regform.getUserId());
			doctorrepo.save(doctor);
			return regrepo.save(regform);
		} else {
			throw new RuntimeException("User not found  for the id");
		}
	}

	///fetching by id
    public RegistrationForm fetchById(long userId) {


        Optional<RegistrationForm> regform=regrepo.findById(userId);

        if(regform.isPresent()) {

            return regform.get();

        }else {

            throw new  RuntimeException("User not found  for the id"+userId);
        }

    }

	// Deleting By id

	public String DeleteById(long userId) {
		List<RegistrationForm> regform = regrepo.findByUserId(userId);
		RegistrationForm form=regform.get(0);
		Doctor doctor= doctorrepo.getById(userId);
		Long docid=doctor.getId();
		System.err.println(docid.equals(form.getUserId()));
		if(docid.equals(form.getUserId()))
		{
			Doctor doc=doctorrepo.getById(userId);			 
			regrepo.delete(regform.get(0));
			doctorrepo.deleteById(doc.getId());			
			return "Registered User is deleted with id" + userId;
		} else {

			throw new RuntimeException("User not found  for the id" + userId);
		}
	}

	
	
	//Adding Location
	public void addLocation(Location location) {
		 locationRepository.save(location);		
	
      }
	
	//fetching all the locations
	 public List<Location> findAll() {
	        return locationRepository.findAll();
	    }

}
