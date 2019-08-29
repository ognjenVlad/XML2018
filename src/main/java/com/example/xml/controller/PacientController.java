package com.example.xml.controller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.xml.model.User;
import com.example.xml.service.PatientService;

@RestController
@RequestMapping(value = "patient")
public class PacientController {

	@Autowired
	PatientService patientService;
	
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<User> getPatient(@RequestParam String id) throws Exception{
        User u = patientService.findById(id);

        return new ResponseEntity<User>(u, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/find-by-username",
            produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.GET)
    public ResponseEntity<User> getPatientByUsername(@RequestParam String username) throws Exception{
        User u = patientService.findByUsername(username);

        return new ResponseEntity<User>(u, HttpStatus.OK);
    }
}
