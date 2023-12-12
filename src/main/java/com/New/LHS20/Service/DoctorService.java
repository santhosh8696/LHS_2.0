package com.New.LHS20.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.New.LHS20.Dao.DoctorPrescriptionRepository;
import com.New.LHS20.Dao.DoctorRepository;
import com.New.LHS20.Dao.PrescriptionRepository;
import com.New.LHS20.Dao.ReceptionRepository;
import com.New.LHS20.Dao.RegistrationRepository;
import com.New.LHS20.Dao.SlotRepo;
import com.New.LHS20.Dao.SpecialityRepository;
import com.New.LHS20.Dao.monitoringDataRepository;
import com.New.LHS20.Dto.SpecialityDto;
import com.New.LHS20.Entity.AdmissionForm;
import com.New.LHS20.Entity.Doctor;
import com.New.LHS20.Entity.Doctor_Prescription;
import com.New.LHS20.Entity.MonitoringData;
import com.New.LHS20.Entity.Patient;
import com.New.LHS20.Entity.RegistrationForm;
import com.New.LHS20.Entity.SlotTime;
import com.New.LHS20.Entity.Speciality;

@Service
public class DoctorService {

	@Autowired
	private RegistrationRepository registrationRepository;

	@Autowired
	private SpecialityRepository specialityRepository;

	@Autowired
	private DoctorRepository doctorrepository;

	@Autowired
	private monitoringDataRepository monitoringDataRepository;

	@Autowired
	private SlotRepo slotRepo;

	@Autowired
	private DoctorPrescriptionRepository doctorPrescriptionRepository;

	
	@Autowired
	private ReceptionRepository receptionrepo;

	@Autowired
	private PrescriptionRepository prescriptionRepository;

	@Autowired
	private DoctorRepository doctorRepository;
	
	
	
     //Doctor can add his Specialty 
	public ResponseEntity addSpec(SpecialityDto specialityDto) {
		RegistrationForm registrationForm = registrationRepository.findByEmail(specialityDto.getEmail());
		if (registrationForm.getRoleName().equals("DOCTOR") || registrationForm.getRoleName().equals("ADMIN")) {
			List<Speciality> specialityList = new ArrayList();
			specialityList.add(new Speciality(specialityDto.getSpeciality(), (int) registrationForm.getUserId()));
			Doctor doctor = doctorrepository.findById(registrationForm.getUserId());
			doctor.setSpeciality(specialityDto.getSpeciality());

			doctorrepository.save(doctor);
			specialityRepository.saveAll(specialityList);
			return new ResponseEntity(specialityList, HttpStatus.ACCEPTED);

		} else {
			return new ResponseEntity("Invalid Authorization", HttpStatus.BAD_REQUEST);
		}
	}

	// Doctor can update his profile
	public RegistrationForm updateMyProfile(RegistrationForm regform) {

		if (registrationRepository.existsByUsername(regform.getUsername())) {
			Doctor doctor = doctorrepository.findById(regform.getUserId());

			registrationRepository.save(regform);
			ModelMapper modelmapper1 = new ModelMapper();
			modelmapper1.map(regform, doctor);
			doctorrepository.save(doctor);
			return registrationRepository.save(regform);
		} else {
			throw new RuntimeException("User not found  for the id");
		}

	}

	public MonitoringData fetchById(Patient patient) {

		List<MonitoringData> monitoring = monitoringDataRepository.findByPatient(patient);
//               MonitoringData   monitoringdata = monitoring.get(0);
		return monitoring.get(0);

	}

	// doctor adding the slot
	public ResponseEntity addSlot(@RequestBody SlotTime slotTime, Authentication authentication) throws ParseException {

		String username = authentication.getName();
		Date slotDate = new SimpleDateFormat("dd/MM/yyyy").parse(slotTime.getDate());
		CharSequence hours = slotTime.getStartTime();

		DateTimeFormatter formatTimeNow = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime timeNow = LocalTime.parse(hours, formatTimeNow);

		RegistrationForm registrationForm = registrationRepository.findByUsername(username);
		slotTime.setDoctorId(registrationForm.getUserId());
		List<SlotTime> listOfSlots = slotRepo.findByDoctorId(registrationForm.getUserId());
		int booked = 0;
		for (SlotTime slotTimeEntity:listOfSlots) {
			if (registrationForm.getRoleName().equals("DOCTOR")
					&& slotTime.getStartTime().equals(slotTimeEntity.getStartTime())
					&& slotTime.getDate().equals(slotTimeEntity.getDate())
					&& slotTime.getEndTime().equals(slotTimeEntity.getEndTime())) {
				booked++;
			}
		}
		if (booked == 0) {
			slotRepo.save(slotTime);
			List<SlotTime> listOfSlotsOutput = new ArrayList<SlotTime>();
			listOfSlotsOutput.add(slotTime);
			return new ResponseEntity(listOfSlotsOutput, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity("Already Added", HttpStatus.BAD_REQUEST);
		}
	}

	// Booking the slot
	public ResponseEntity bookAppointmnet(Authentication authentication, @RequestBody SlotTime bookSlot) {

		String username = authentication.getName();
		RegistrationForm registrationForm = registrationRepository.findByUsername(username);
		bookSlot.setPatientId(registrationForm.getUserId());
		List<SlotTime> listOfSlots = slotRepo.findByDoctorId(bookSlot.getDoctorId());
		Iterator iterator = listOfSlots.iterator();
		int booked = 0;
		while (iterator.hasNext()) {
			SlotTime slotTimeEntity = (SlotTime) iterator.next();
			bookSlot.setId(slotTimeEntity.getId());
			if (slotTimeEntity.getPatientId() == null && slotRepo.existsByDoctorId(bookSlot.getDoctorId())
					&& bookSlot.getStartTime().equals(slotTimeEntity.getStartTime())
					&& bookSlot.getDate().equals(slotTimeEntity.getDate())
					&& bookSlot.getEndTime().equals(slotTimeEntity.getEndTime())) {
				bookSlot.setStatus("Pending");
				slotRepo.save(bookSlot);
				booked++;
			}
		}

		if (booked > 0) {
			return new ResponseEntity("Booked", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity("Already Booked", HttpStatus.BAD_REQUEST);
		}
	}

	// confirm slot
	public ResponseEntity confirmSlot(Authentication authentication, @RequestBody SlotTime bookSlot) {

		String username = authentication.getName();
		RegistrationForm registrationForm = registrationRepository.findByUsername(username);
		List<SlotTime> listOfSlots = slotRepo.findByDoctorId(registrationForm.getUserId());
		Iterator iterator = listOfSlots.iterator();
		int booked = 0;
		while (iterator.hasNext()) {
			SlotTime slotTime2 = (SlotTime) iterator.next();
			if (slotTime2.getPatientId() != null && slotRepo.existsByDoctorId(slotTime2.getDoctorId())
					&& bookSlot.getStartTime().equals(slotTime2.getStartTime())) {

				slotTime2.setStatus("Confirmed");
				AdmissionForm admissionform = new AdmissionForm();
				admissionform.setDisease(slotTime2.getDisease());
				admissionform.setRegdNo(slotTime2.getPatientId().intValue());
				admissionform.setAdmissionDate(slotTime2.getDate());
				admissionform.setDoctor(registrationForm.getUsername());

				receptionrepo.save(admissionform);

				slotRepo.save(slotTime2);

				booked++;
			}
		}

		if (booked > 0) {
			return new ResponseEntity("Confirmed", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity("not confirmed", HttpStatus.BAD_REQUEST);
		}

	}

	// cancel slot
	public ResponseEntity cancelslot(Authentication authentication, @RequestBody SlotTime bookSlot) {

		String username = authentication.getName();
		RegistrationForm registrationForm = registrationRepository.findByUsername(username);
		bookSlot.setPatientId(registrationForm.getUserId());
		List<SlotTime> listOfSlots = slotRepo.findByDoctorId(bookSlot.getDoctorId());
		int booked = 0;
		for (SlotTime slotTime2 :listOfSlots) {
			bookSlot.setId(slotTime2.getId());
			if (slotTime2.getPatientId() != null && slotRepo.existsByDoctorId(bookSlot.getDoctorId())
					&& bookSlot.getStartTime().equals(slotTime2.getStartTime())) {
				bookSlot.setStatus("Cancelled");
				slotRepo.save(bookSlot);
				booked++;
			}
		}

		if (booked > 0) {
			return new ResponseEntity("Cancelled", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity("Not Cancelled", HttpStatus.BAD_REQUEST);
		}

	}

	// get appointments by the date
	public ResponseEntity getAppointbydate(Authentication authentication, @RequestBody SlotTime bookSlot) {
		String uname = authentication.getName();
		RegistrationForm registrationForm = registrationRepository.findByUsername(uname);
		List<SlotTime> slotDate = slotRepo.findByDate(bookSlot.getDate());
		Iterator iterator = slotDate.iterator();
		List getDate = new ArrayList<>();
		while (iterator.hasNext()) {
			SlotTime slotTime2 = (SlotTime) iterator.next();
			if (String.valueOf(registrationForm.getUserId()).equals(slotTime2.getDoctorId().toString())) {
				slotTime2.setDoctorId(registrationForm.getUserId());
				getDate.add(slotTime2);
			}
		}
		return new ResponseEntity(getDate, HttpStatus.OK);
	}

	// Doctor can fetch upcomming appointments
	public ResponseEntity getcurrentdateappointments(Authentication authentication) {
		String userName = authentication.getName();
		RegistrationForm form = registrationRepository.findByUsername(userName);
		List<SlotTime> listOfSlots = slotRepo.findByDoctorId(form.getUserId());
		SlotTime slotTime = listOfSlots.get(0);
		Long docId = slotTime.getDoctorId();
		DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String today = LocalDate.now().format(format1);
		String tillDate = LocalDate.now().plusDays(7).format(format1);

		return new ResponseEntity<>(slotRepo.findBySevenDaysSlots1(today, tillDate, docId), HttpStatus.OK);
	}

	// Doctor can add medicines
	public ResponseEntity addmedicines(Authentication authentication,
			@RequestBody Doctor_Prescription doctorprescription) {
		String userName = authentication.getName();
		Doctor doctor = doctorRepository.findByEmail(userName);
		List<Doctor_Prescription> prescriptionList = prescriptionRepository.findByDoctor(doctor);
		int listCount = 0;
		for (Doctor_Prescription prescription2 : prescriptionList) {

			if (prescription2.getDuration() == null) {
				doctorprescription.setId(prescription2.getId());
				doctorprescription.setDoctor(doctor);
				doctorPrescriptionRepository.save(doctorprescription);
				listCount++;
			}
		}
		if (listCount == 0) {
			doctorprescription.setDoctor(prescriptionList.get(0).getDoctor());
			doctorPrescriptionRepository.save(doctorprescription);
		}
		return new ResponseEntity<>(doctorprescription, HttpStatus.ACCEPTED);
	}

}
