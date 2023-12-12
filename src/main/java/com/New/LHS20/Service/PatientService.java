package com.New.LHS20.Service;

import java.util.Collection;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.New.LHS20.Dao.DoctorRepository;
import com.New.LHS20.Dao.PatientRepository;
import com.New.LHS20.Dao.RegistrationRepository;
import com.New.LHS20.Entity.Doctor;
import com.New.LHS20.Entity.Patient;
import com.New.LHS20.Entity.RegistrationForm;


 
@Service
public class PatientService {
	 
   @Autowired
   private  DoctorRepository doctorRepository;
   
   @Autowired
   private RegistrationRepository regrepo;
   
   @Autowired
   private PatientRepository patientrepository;
   
//
//	    @Autowired
//	    public PatientService(PatientRepository patientRepository) {
//	        this.patientRepository = patientRepository;
//	    }
//
     
   
   
   //Patient can find the list of doctors
	    public Collection<Doctor> findAll() {
	        return doctorRepository.findAll();
	    }
	    
	    
 //  Patient can update his profile
		  public RegistrationForm updateMyProfile(RegistrationForm regform) {
		    		if (regrepo.existsByUsername(regform.getUsername())) {
          	Patient patient = patientrepository.findByUserId(regform.getUserId());
 
          	                regrepo.save(regform);
          	         ModelMapper modelmapper1 = new ModelMapper();
			         modelmapper1.map(regform, patient);
              			patientrepository.save(patient);
		    			return regrepo.save(regform);
		    		} else {
		    			throw new RuntimeException("User not found  for the id");
		    }
		    	
	}
		  
		  
		  
		  
//
//	    @Transactional
//	    public Optional<Patient> findById(Integer id) {
//	        return patientRepository.findById(id);
//	    }
//
//	    @Transactional
//	    public Patient save(Patient patient) {
//	        if(alreadyExists(patient)) {
//	            throw new ResourceAlreadyExistsException("This patient already exists");
//	        }
//	        return patientRepository.save(patient);
//	    }
//
//	    private boolean alreadyExists(Patient patient) {
//	        SocialSecurityNumber socialSecurityNumber = patient.getSocialSecurityNumber();
//	        Optional<Patient> patientOptional = patientRepository
//	                .findBySocialSecurityNumber(socialSecurityNumber);
//
//	        return patientOptional.isPresent();
//	    }
//	}
//
//
//


}
