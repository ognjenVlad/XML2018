package com.example.xml.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xml.dtos.RegisterDTO;
import com.example.xml.model.patient.Patient;
import com.example.xml.model.user.User;
import com.example.xml.repository.TechnicianRepository;
import com.example.xml.util.Roles;

@Service
public class TechnicianService {
	public final static String userId = "http://www.health_care.com/technician/";
	@Autowired
	TechnicianRepository technicianRepository;
	
	public void save(User technician) {
		try {
			technician.setRole(Roles.TECHNICIAN.toString());
			technicianRepository.save(technician);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public User findById(String id) {
		try {
			return technicianRepository.findPacientById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public User findByUsername(String username) {
		try {
			return technicianRepository.findByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public User mapDtoToUser(RegisterDTO dto) {
		User user = new User();
		User.Name name = new User.Name();
		name.setValue(dto.getName());
		user.setName(name);
		user.setJmbg(dto.getJmbg());
		user.setLastname(dto.getLastname());
		user.setPassword(dto.getPassword());
		user.setUsername(dto.getUsername());
		user.setId(userId + dto.getJmbg());
		return user;
	}
}
