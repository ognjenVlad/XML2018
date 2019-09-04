package com.example.xml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.xml.dtos.DoctorDTO;
import com.example.xml.dtos.LoginDTO;
import com.example.xml.dtos.RegisterDTO;
import com.example.xml.model.Doctor;
import com.example.xml.model.patient.Patient;
import com.example.xml.model.user.User;
import com.example.xml.security.JWTUtils;
import com.example.xml.service.PatientService;
import com.example.xml.service.TechnicianService;
import com.example.xml.service.UserDetailsService;
import org.springframework.http.MediaType;

@RestController
@CrossOrigin(value = "http://localhost:4200")
@RequestMapping(value = "auth")
public class AuthController {
	
	
	@Autowired
    private JWTUtils jwtUtils;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private TechnicianService technicianService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@RequestMapping(value = "/login",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        try {
        	UserDetails details = userDetailsService.loadUserByUsername(loginDTO.getUsername());
        	System.out.println("details:::::::" + details.getUsername());
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(), loginDTO.getPassword());
            authenticationManager.authenticate(token);
            return new ResponseEntity<>(jwtUtils.generateToken(details), HttpStatus.OK);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }
	
	@RequestMapping(value = "/register",
			consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.POST)
	public ResponseEntity<User> register(@RequestBody RegisterDTO newUser) {
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		Patient u = this.patientService.mapDtoToUser(newUser);
		this.patientService.save(u);
		return new ResponseEntity<User>(u, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/create-technician",
			consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.POST)
    public ResponseEntity<User> createTechnician(@RequestBody RegisterDTO newUser) throws Exception{
		User d = this.technicianService.mapDtoToUser(newUser);
		this.technicianService.save(d);
        return new ResponseEntity<User>(d, HttpStatus.OK);
    }
}
