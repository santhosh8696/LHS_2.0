package com.New.LHS20.Controller;

import java.util.Collection;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.New.LHS20.Entity.Doctor;
import com.New.LHS20.Entity.RegistrationForm;
import com.New.LHS20.Service.PatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
	
 
	 
        @Autowired
	    private  PatientService patientService;
 
      
        
        
       
        //Patient can Update his profile
        @PutMapping("/update")
        public ResponseEntity updateMyProfile(@RequestBody  RegistrationForm regform)
        {
          
          RegistrationForm regform1 = patientService.updateMyProfile(regform);
          System.err.println(regform1);
            return new ResponseEntity(regform1, HttpStatus.OK);
        }
        
        
        //patient can find all the list of doctors with speciality
	    @GetMapping("/alldoctors")
	    Collection<Doctor> findAll() {
	        return patientService.findAll();
	        
	        }
	    
	    
	    
	    
	    
	    
}
