package com.New.LHS20.Controller;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.New.LHS20.Dao.DoctorPrescriptionRepository;
import com.New.LHS20.Dao.DoctorRepository;
import com.New.LHS20.Dao.PrescriptionRepository;
import com.New.LHS20.Dao.ReceptionRepository;
import com.New.LHS20.Dao.RegistrationRepository;
import com.New.LHS20.Dao.SlotRepo;
import com.New.LHS20.Dao.SpecialityRepository;
import com.New.LHS20.Dto.SpecialityDto;
import com.New.LHS20.Entity.AdmissionForm;
import com.New.LHS20.Entity.Doctor;
import com.New.LHS20.Entity.Doctor_Prescription;
import com.New.LHS20.Entity.MonitoringData;
import com.New.LHS20.Entity.Patient;
import com.New.LHS20.Entity.RegistrationForm;
import com.New.LHS20.Entity.SlotTime;
import com.New.LHS20.Entity.Speciality;
import com.New.LHS20.Security.JWTUtility;
import com.New.LHS20.Service.DoctorService;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
	@Autowired
	private DoctorService doctorService;


	@Autowired
	private SlotRepo slotRepo;

	@Autowired
	private DoctorPrescriptionRepository doctorPrescriptionRepository;

	@Autowired
	JWTUtility jwtUtility;
//	

	@Autowired
	private RegistrationRepository registrationRepository;

	@Autowired
	private SpecialityRepository specialityRepository;

	@Autowired
	private AdmissionForm admissionform;

	@Autowired
	private ReceptionRepository receptionrepo;

	@Autowired
	private PrescriptionRepository prescriptionRepository;

	@Autowired
	private DoctorRepository doctorRepository;



	// doctor can add speciality using his email
	@PostMapping("/addspeciality")
	public ResponseEntity addSpeciality(@RequestBody SpecialityDto specialityDto) {
		return doctorService.addSpec(specialityDto);
	}

	// Doctor can schedule an appointment
	@PostMapping("/addSlots")
	public ResponseEntity addSlot(@RequestBody SlotTime slotTime2, Authentication authentication)
			throws ParseException {
		return doctorService.addSlot(slotTime2, authentication);
	}

	// admin can get all the scheduled and booked slots
	@GetMapping("/getAllSlots")
	public List<SlotTime> getAll() {
		return slotRepo.findAll();
	}

	// doctor can delete the slot
	@DeleteMapping("/deleteSlots")
	public List<SlotTime> updateAppointment(@RequestBody SlotTime slotTime2) {
		slotRepo.delete(slotTime2);
		return slotRepo.findAll();
	}

	// patient can book the slot
	@PostMapping("/bookslot")
	public ResponseEntity bookAppointmnet(Authentication authentication, @RequestBody SlotTime bookSlot) {
		return doctorService.bookAppointmnet(authentication, bookSlot);
	}

	// ADMIN-List of appointments for doctor using doctor id
	@GetMapping("/getpatientbydid")
	public List<SlotTime> getPatientByDid(Authentication authentication) {
		String username = authentication.getName();
		RegistrationForm registrationForm = registrationRepository.findByUsername(username);
		List<SlotTime> bookSlot2 = slotRepo.findByDoctorId(registrationForm.getUserId());
		return bookSlot2;
	}

	// Both patients and Admin can fetch doctors using doctor speciality
	@GetMapping("/getspeciality")
	public List<Speciality> getspecaility() {
		return specialityRepository.findAll();
	}

	// Patient can able to fetch the doctors with speciality
	@PostMapping("/getdocbyspeciality")
	public List<Doctor> getdocbyspeciality(@RequestBody Speciality speciality) {
		return doctorRepository.findBySpeciality(speciality.getName());
	}

	// doctor can confirm the slot
	@PostMapping("/confirmslot")
	public ResponseEntity confirmSlot(Authentication authentication, @RequestBody SlotTime bookSlot) {
		return doctorService.confirmSlot(authentication, bookSlot);
	}

	// Doctor can cancel the slot
	@PostMapping("/cancelslot")
	public ResponseEntity cancelslot(Authentication authentication, @RequestBody SlotTime bookSlot) {
		return doctorService.cancelslot(authentication, bookSlot);
	}

	// Doctor will login and he can see appointments by date
	@GetMapping("/getappointbydate")
	public ResponseEntity getAppointbydate(Authentication authentication, @RequestBody SlotTime bookSlot) {

		return doctorService.getAppointbydate(authentication, bookSlot);
	}

	// Doctor can fetch upcomming appointments
	@GetMapping("/getupcommingappointments")
	public ResponseEntity getcurrentdateappointments(Authentication authentication) {

		return doctorService.getcurrentdateappointments(authentication);
	}

	// Doctor can Update his profile
	@PutMapping("/update")
	public ResponseEntity updateMyProfile(@RequestBody RegistrationForm regform) {
		RegistrationForm registrationForm = doctorService.updateMyProfile(regform);
		return new ResponseEntity(registrationForm, HttpStatus.OK);
	}

	// Doctor can add medicines
	@PostMapping("/addMedicines")
	public ResponseEntity addmedicines(Authentication authentication,
			@RequestBody Doctor_Prescription doctorprescription) {

		return doctorService.addmedicines(authentication, doctorprescription);
	}

// doctor can fetch pre-monitoring data  by using patient id
	@GetMapping("/reg/{patient}")
	public ResponseEntity get(@PathVariable Patient patient) {
		MonitoringData monitoringData = doctorService.fetchById(patient);
		return new ResponseEntity(monitoringData, HttpStatus.OK);
	}
}
