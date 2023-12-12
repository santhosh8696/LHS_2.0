package com.New.LHS20.Controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.New.LHS20.Dao.DoctorRepository;
import com.New.LHS20.Entity.Doctor;
import com.New.LHS20.Entity.MonitoringData;
import com.New.LHS20.Entity.Patient;
import com.New.LHS20.Entity.Speciality;
import com.New.LHS20.Service.DoctorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@AutoConfigureWebMvc
//@WebMvcTest(value = DoctorController.class)
class DoctorControllerTest {

	  @Autowired
	  private WebApplicationContext webApplicationContext;
	  
	  @Autowired
	  private MockMvc mockMvc;

	
	@MockBean
	private DoctorService doctorService;
	
	@MockBean
	private DoctorRepository doctorRepository;

	@Test
	public void fetchById() throws Exception {
		MonitoringData mockTicket = new MonitoringData();
		mockTicket.setId(1);
		mockTicket.setPatient(new Patient(1,"", null, null, null, null, null, null, null, null));
		mockTicket.setTemperature("55");

		Mockito.when(doctorService.fetchById(Mockito.any())).thenReturn(mockTicket);
		
		String URI = "/doctors/reg/1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(mockTicket);
		System.err.println(expectedJson);
		String outputInJson = result.getResponse().getContentAsString();
		System.err.println(outputInJson);
		assertThat(outputInJson).isEqualTo(expectedJson);
	}
	
	@Test
	public void doctorBySpeciality() throws Exception {
		Doctor mockTicket = new Doctor();
		mockTicket.setId(1);
		mockTicket.setFirstName("santhosh");
		mockTicket.setSpeciality("Dentist");

		Speciality speciality=new Speciality();
		speciality.setDoctorId(1);
		speciality.setName("santhosh");
		Mockito.when(doctorRepository.findBySpeciality(speciality.getName())).thenReturn(Collections.singletonList(mockTicket));
		
		String URI = "/doctors/getdocbyspeciality";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(mockTicket);
		String outputInJson = result.getResponse().getContentAsString();
		assertThat(outputInJson).isEqualTo(expectedJson);
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
}
