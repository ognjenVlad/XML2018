package com.example.xml.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.xml.dtos.DoctorDTO;
import com.example.xml.dtos.RegisterDTO;
import com.example.xml.model.Doctor;
import com.example.xml.model.user.User;
import com.example.xml.service.DoctorServiceImpl;

@RestController
@RequestMapping(value = "doctor")
public class DoctorController {
	@Autowired
	DoctorServiceImpl doctorService;
	
	@RequestMapping(value = "/all-doctors",
            produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Doctor>> getAllDoctors() throws Exception{
		ArrayList<Doctor> doctors = doctorService.getAll();

        return new ResponseEntity<ArrayList<Doctor>>(doctors, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/create",
			consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.POST)
    public ResponseEntity<Doctor> save(@RequestBody DoctorDTO newUser) throws Exception{
		Doctor d = this.doctorService.mapDtoToUser(newUser);
		this.doctorService.save(d);
        return new ResponseEntity<Doctor>(d, HttpStatus.OK);
    }
}
