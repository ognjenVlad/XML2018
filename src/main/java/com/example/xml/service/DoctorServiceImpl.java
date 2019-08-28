package com.example.xml.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xml.dtos.DoctorDTO;
import com.example.xml.dtos.RegisterDTO;
import com.example.xml.model.Doctor;
import com.example.xml.model.Patient;
import com.example.xml.model.Record;
import com.example.xml.model.User;
import com.example.xml.repository.DoctorRepository;
import com.example.xml.util.Roles;

@Service
public class DoctorServiceImpl implements DoctorService {
	public final static String userId = "http://www.health_care.com/doctor/";
	@Autowired
	DoctorRepository doctorRepository;
	
	public ArrayList<Doctor> getAll() {
		try {
			return doctorRepository.getAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void save(Doctor doctor) {
		try {
			doctorRepository.save(doctor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Doctor mapDtoToUser(DoctorDTO dto) {
		Doctor user = new Doctor();
		User.Name name = new User.Name();
		name.setValue(dto.getName());
		user.setName(name);
		user.setJmbg(dto.getJmbg());
		user.setLastname(dto.getLastname());
		user.setPassword(dto.getPassword());
		user.setUsername(dto.getUsername());
		user.setId(userId + dto.getJmbg());
		user.setSpecialty(dto.getSpecialty());
		user.setRole(Roles.DOCTOR.toString());
		return user;
	}
}
