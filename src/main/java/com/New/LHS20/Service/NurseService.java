package com.New.LHS20.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.Id;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.New.LHS20.Dao.NurseRepository;
import com.New.LHS20.Dao.ReceptionRepository;
import com.New.LHS20.Dao.RegistrationRepository;
import com.New.LHS20.Dao.SlotRepo;
import com.New.LHS20.Dao.SupplimentsRepository;
import com.New.LHS20.Dao.monitoringDataRepository;
import com.New.LHS20.Entity.AdmissionForm;
import com.New.LHS20.Entity.MonitoringData;
import com.New.LHS20.Entity.Nurse;
import com.New.LHS20.Entity.RegistrationForm;
import com.New.LHS20.Entity.SlotTime;
import com.New.LHS20.Entity.Suppliments;

@Service
public class NurseService {
	@Autowired
	private NurseRepository nurserepo;
	@Autowired
	private RegistrationRepository regrepo;
	
	@Autowired
	private ReceptionRepository receprepo;
	@Autowired
	private SlotRepo slotrepo;
	
	@Autowired
	SupplimentsRepository supplimentsRepository;
	
	@Autowired
	private monitoringDataRepository monitoringdatarepository;
 
	

//	public List<AdmissionForm> getByDoctor(String doctor) {
//	 return nurserepo.findAll();
//	}
//
//	
	 
	public List<AdmissionForm> getAllAdmittedPatients() {
	    return receprepo.findAll();
	}

	
	
	//Nurse can update his profile
	public RegistrationForm updateMyProfile(RegistrationForm regform) {
	     
	    if (regrepo.existsByUsername(regform.getUsername())) {
	         Nurse nurse = nurserepo.findByUserId(regform.getUserId());
	
	             regrepo.save(regform);
	                 ModelMapper modelmapper1 = new ModelMapper();
	                 modelmapper1.map(regform, nurse);
	                 nurserepo.save(nurse);
	                    return regrepo.save(regform);
	                } 
	                else {
	                    throw new RuntimeException("User not found  for the id");
	        }
	            

	}

	

	// Nurse was able to see all the appointments upto one week
      public ResponseEntity getupcommingappointments() {
		DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String today = LocalDate.now().format(format1);
		String tillDate = LocalDate.now().plusDays(7).format(format1);
		List<SlotTime> list = slotrepo.findBySevenDaysSlots(today, tillDate);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
  
      // Nurse can able to see today appointments
 public ResponseEntity getcurrentappointments() {
  		DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  		String today = LocalDate.now().format(format1);

  		List<SlotTime> list = slotrepo.findByDate(today);
  		return new ResponseEntity<>(list, HttpStatus.OK);
  	}


//Nurse can add suppliments

	public ResponseEntity addsuppliments(@RequestBody Suppliments suppliments) {

		//String userName = "padamavathilancesoft@gmail.com";
		List<Suppliments> suppliments2 = supplimentsRepository.findByPatient(suppliments.getPatient());
		Iterator iterator = suppliments2.iterator();
		int i = 0;
		for (Suppliments suppliments3 : suppliments2) {
			if (suppliments3.getQuantity() == null) {
				suppliments.setQuantity("0");
				suppliments.setId(suppliments3.getId());
				supplimentsRepository.save(suppliments3);
				i++;
			}
		}
		if (i == 0) {
			supplimentsRepository.save(suppliments);
		}
		return null;

	}

	//Nurse can add Monitoring Data
	
	public ResponseEntity addMonitoringData(@RequestBody MonitoringData monitoringData) {
		List<MonitoringData> datas = monitoringdatarepository.findByPatient(monitoringData.getPatient());
		MonitoringData data = datas.get(0);
 
		monitoringData.setPatient(data.getPatient());
		monitoringData.setId(data.getId());
		return new ResponseEntity<>(monitoringdatarepository.save(monitoringData), HttpStatus.ACCEPTED);
	}



	 
}