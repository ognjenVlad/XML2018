package com.example.xml.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xml.dtos.RegisterDTO;
import com.example.xml.model.Doctor;
import com.example.xml.model.patient.Patient;
import com.example.xml.model.record.Record;
import com.example.xml.model.user.User;
import com.example.xml.repository.PatientRepository;
import com.example.xml.repository.RecordRepository;
import com.example.xml.util.Roles;

@Service
public class PatientService  {
	public final static String userId = "http://www.health_care.com/user/";
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	RecordRepository recordRepository;

	public void save(Patient pacient) {
		try {
			patientRepository.save(pacient);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Patient> getAll() {
		try {
			return patientRepository.getAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Patient findById(String id) {
		try {
			return patientRepository.findPacientById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Patient findByUsername(String username) {
		try {
			return patientRepository.findByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Patient> simpleSearch(String text) {
		try {
			return patientRepository.simpleSearch(text);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Record> simpleSearchRecord(String text) {
		try {
			return recordRepository.simpleSearch(text);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> advancedSearchRecord(String criteria, String text) {
		try {
			return recordRepository.advancedSearch(criteria, text);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public com.example.xml.model.patient.Patient mapDtoToUser(RegisterDTO dto) {
		Patient user = new Patient();
		User.Name name = new User.Name();
		name.setValue(dto.getName());
		user.setName(name);
		user.setJmbg(dto.getJmbg());
		user.setLastname(dto.getLastname());
		user.setPassword(dto.getPassword());
		user.setUsername(dto.getUsername());
		user.setId(userId + dto.getJmbg());
		user.setLbo(dto.getLbo());
		user.setPhone(dto.getPhone());
		user.setRole(Roles.PATIENT.toString());
		return user;
	}
}
