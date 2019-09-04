package com.example.xml.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.xml.model.patient.Patient;
import com.example.xml.model.record.Record;
import com.example.xml.model.user.User;
import com.example.xml.service.PatientService;

@RestController
@RequestMapping(value = "patient")
public class PatientController {

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
	
	@RequestMapping(value = "/search",
            produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.GET)
    public ResponseEntity<List<Record>> simpleSearch(@RequestParam String text) throws Exception{
		List<Record> patients = patientService.simpleSearchRecord(text);

        return new ResponseEntity<List<Record>>(patients, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/advanced-search",
            produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.GET)
    public ResponseEntity<List<String>> advancedSearch(@RequestParam String criteria, @RequestParam String text) throws Exception{
		List<String> results = patientService.advancedSearchRecord(criteria, text);

        return new ResponseEntity<List<String>>(results, HttpStatus.OK);
    }
}
