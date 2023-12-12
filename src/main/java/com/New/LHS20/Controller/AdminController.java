package com.New.LHS20.Controller;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.New.LHS20.Dao.DoctorRepository;
import com.New.LHS20.Dto.JwtRequestPayload;
import com.New.LHS20.Dto.JwtResponse;
import com.New.LHS20.Entity.Location;
//
//import com.New.LHS20.Dto.JwtRequestPayload;
//import com.New.LHS20.Dto.JwtResponse;
import com.New.LHS20.Entity.RegistrationForm;
import com.New.LHS20.Security.JWTUtility;
//import com.New.LHS20.Security.JWTUtility;
import com.New.LHS20.Security.MyUserDetails;
import com.New.LHS20.Service.AdminService;
import com.New.LHS20.Service.RegistrationService;

@RestController
@RequestMapping("/api")
public class AdminController {

	@Autowired
	JWTUtility jwtUtility;

	@Autowired
	MyUserDetails Mudetails;

	@Autowired
	AuthenticationManager authenticationManager;
	
	

	private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private RegistrationService regservice;

	@Autowired
	private AdminService adminService;

	
	//adding admin
	@PostMapping("/admin")
	public ResponseEntity addAdmin(@RequestBody RegistrationForm regform) {

		adminService.addAdmin(regform);

		return new ResponseEntity("Registered successfully", HttpStatus.OK);

	}
	
 //adding doctor
	@PostMapping("/doctor")

	public ResponseEntity addDoctor(@RequestBody RegistrationForm regform) {

		adminService.addDoctor(regform);
		System.out.println("Hello");

		return new ResponseEntity("Registered successfully", HttpStatus.OK);

	}

	
	//adding nurse
	@PostMapping("/nurse")

	public ResponseEntity addNurse(@RequestBody RegistrationForm regform) {

		System.out.println("Hello");
		adminService.addNurse(regform);

		return new ResponseEntity("Registered successfully", HttpStatus.OK);

	}

	
	//adding pharmacist
	@PostMapping("/pharmacist")

	public ResponseEntity addPharmacist(@RequestBody RegistrationForm regform) {

		System.out.println("Hello");
		adminService.addPharmacist(regform);

		return new ResponseEntity("Registered successfully", HttpStatus.OK);
	}
	
	
	//adding receptionist
	@PostMapping("/receptionist")

    public ResponseEntity addReceptionist(@RequestBody RegistrationForm regform) {

        System.out.println("Hello");
        adminService.addReceptionist(regform);

        return new ResponseEntity("Registered successfully", HttpStatus.OK);
    }


	@Autowired
	DoctorRepository doctorRepository;
	
 //Admin can Update Details
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody RegistrationForm regform)
    {
      
      RegistrationForm regform1 =adminService.updateDetails(regform);
      System.err.println(regform1);
        return new ResponseEntity(regform1, HttpStatus.OK);
    }


    // admin can fetch details by using id
    @GetMapping("/reg/{userId}")
    public ResponseEntity get(@PathVariable  long userId) {
        RegistrationForm regform=adminService.fetchById(userId);
        System.out.println(regform);

        return new ResponseEntity(regform , HttpStatus.OK);
    }




    //admin can Delete details by using id
    @DeleteMapping("/reg/{userId}")
    public ResponseEntity get1(@PathVariable  long userId) {
        adminService.DeleteById(userId);
         return new ResponseEntity("Deleted Successfully", HttpStatus.OK);
    }



    @GetMapping("/gg")
    public String getUser() {
        System.out.println("hi I am admin");
        return adminService.test();

    }

    
    //admin fetching by role name
    @GetMapping("/{roleName}")
    public List<RegistrationForm> getUser(@PathVariable("roleName") String rolename) {
        return adminService.getRole(rolename);
    }

    
    
    //admin can add Location
    @PostMapping("/addLocation")

    public ResponseEntity addLocation(@RequestBody Location location) {

        
        adminService.addLocation(location);

        return new ResponseEntity("Location added successfully", HttpStatus.OK);
    }

    //fetching all locations
    @GetMapping("/allLocations")
    Collection<Location> findAll() {
        return adminService.findAll();
    }
     
    
    
    

    //Authentication
    @PostMapping("/authenticate")
    public JwtResponse generate(@RequestBody JwtRequestPayload jwtRequestPayload) {

        System.out.println("kkkkkkkk");

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequestPayload.getUsername(),
                    jwtRequestPayload.getPassword()));
        }

        catch (BadCredentialsException e) {

            throw new RuntimeException(e.getMessage());

        }

        UserDetails serv = Mudetails.loadUserByUsername(jwtRequestPayload.getUsername());

        String token = jwtUtility.generateToken(serv);

        return new JwtResponse(token);

    }

}