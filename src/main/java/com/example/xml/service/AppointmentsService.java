package com.example.xml.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xml.dtos.AppointmentDTO;
import com.example.xml.model.Appointment;
import com.example.xml.model.Doctor;
import com.example.xml.repository.AppointmentsRepository;
import com.example.xml.repository.DoctorRepository;

@Service
public class AppointmentsService {
	@Autowired
	AppointmentsRepository appointmentsRepository;
	
	@Autowired
	DoctorRepository doctorRepository;
	
	public void save(Appointment a) {
		try {
			a.setId(a.getDoctorId() + "_" + a.getPatientJmbg());
			appointmentsRepository.save(a);
			System.out.println("123" + a.getDoctorId());
			Doctor doc = doctorRepository.findById(a.getDoctorId());
			List<String> appointments = doc.getAppointmentIds();
			System.out.println(a.getId());
			appointments.add(a.getId());
			doc.setAppointmentIds(appointments);
			for(String s: doc.getAppointmentIds()) {
				System.out.println(s);
			}
			System.out.println(doc.getAppointmentIds().size());
			doctorRepository.save(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Appointment> allAppointmentsForDoc(String id) {
		try {
			return appointmentsRepository.allAppointmentsForDoc(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Appointment mapAppointment(AppointmentDTO dto) {
		Appointment appointment = new Appointment();
		appointment.setDoctorId(dto.getDoctorId());
		appointment.setId(dto.getId());
		appointment.setIsConfirmed(dto.getIsConfirmed());
		appointment.setPatientJmbg(dto.getPatientJmbg());
		appointment.setTime(dto.getTime());
		appointment.setDate(dto.getDate());
		return appointment;
	}
}
