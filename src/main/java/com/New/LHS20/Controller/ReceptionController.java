package com.New.LHS20.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.New.LHS20.Dao.ReceptionRepository;
import com.New.LHS20.Entity.AdmissionForm;
import com.New.LHS20.Service.ReceptionService;

@RestController
@RequestMapping("/api")
public class ReceptionController {
	@Autowired
	ReceptionService recepservice;
	
	
 	
	
 
  
//Receptionist can update admitted patient Details
	@PutMapping("/reception")
  public AdmissionForm updateAdmittedPatient(@RequestBody AdmissionForm admissionform) {
   
  return  this.recepservice.addAdmittedPatients(admissionform);

  }
	
	@Autowired
	ReceptionRepository receptionRepository;
	
	
//Receptionist can fetch admitted patient details  using pat id
	@GetMapping("/getPatientById")
	  public List<AdmissionForm> getPatientById(@RequestBody AdmissionForm admissionform) {	
	  return  receptionRepository.findByRegdNo(admissionform.getRegdNo());

	  }

	
	}
