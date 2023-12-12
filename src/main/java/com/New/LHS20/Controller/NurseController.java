package com.New.LHS20.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.New.LHS20.Dao.RegistrationRepository;
import com.New.LHS20.Dao.SlotRepo;
import com.New.LHS20.Dao.monitoringDataRepository;
import com.New.LHS20.Entity.AdmissionForm;
import com.New.LHS20.Entity.MonitoringData;
import com.New.LHS20.Entity.RegistrationForm;
import com.New.LHS20.Entity.Suppliments;
import com.New.LHS20.Service.NurseService;

@RestController
@RequestMapping("/nurse")
public class NurseController {
	@Autowired
	private NurseService nurseservice;
	@Autowired
	private RegistrationRepository registrationrepository;
	@Autowired
	private SlotRepo slotrepo;

	@Autowired
	private monitoringDataRepository monitoringdatarepository;


	
	//Nurse can able to see all the admitted patients
	@GetMapping("/pull")
	public List<AdmissionForm> getAllAdmittedPatients()

	{
		return nurseservice.getAllAdmittedPatients();
	}

	// Nurse can Update his profile
	@PutMapping("/update")
	public ResponseEntity updateMyProfile(@RequestBody RegistrationForm regform) {

		RegistrationForm regform1 = nurseservice.updateMyProfile(regform);
		System.err.println(regform1);
		return new ResponseEntity(regform1, HttpStatus.OK);
	}

	// Nurse was able to see all the appointments upto one week
	@GetMapping("/getupcommingappointments")
	public ResponseEntity getupcommingappointments() {
		return nurseservice.getupcommingappointments();
	}

	// Nurse can able to see today appointments
	@GetMapping("/gettodayappointments")
	public ResponseEntity getcurrentappointments() {
	  return nurseservice.getcurrentappointments();
	}

	
	//Nurse can add suppliments
	@PostMapping("/addsuppliments")
	public ResponseEntity addsuppliments(@RequestBody Suppliments suppliments) {

		return nurseservice.addsuppliments(suppliments);

	}
  
	//Nurse can add Monitoring Data
	@PostMapping("/addMonitoringData")
	public ResponseEntity addMonitoringData(@RequestBody MonitoringData monitoringData) {
	    return nurseservice.addMonitoringData(monitoringData);
	}

}
