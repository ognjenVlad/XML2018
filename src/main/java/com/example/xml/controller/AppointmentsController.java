package com.example.xml.controller;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.xml.dtos.AppointmentDTO;
import com.example.xml.dtos.DoctorDTO;
import com.example.xml.model.Appointment;
import com.example.xml.service.AppointmentsService;

@RestController
@RequestMapping(value = "appointments")
public class AppointmentsController {
	@Autowired
	private AppointmentsService appointmentsService;
	
	@RequestMapping(value = "/doctor",
            produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Appointment>> allAppointmentsForDoc(@RequestParam String id) throws Exception{
		ArrayList<Appointment> appointments = appointmentsService.allAppointmentsForDoc(id);

        return new ResponseEntity<ArrayList<Appointment>>(appointments, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/create",
			consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.POST)
    public ResponseEntity<Appointment> create(@RequestBody AppointmentDTO appointment) throws Exception{
		Appointment a = appointmentsService.mapAppointment(appointment);
		appointmentsService.save(a);

        return new ResponseEntity<Appointment>(a, HttpStatus.OK);
    }

}
