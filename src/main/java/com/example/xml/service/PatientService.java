package com.example.xml.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xml.dtos.RegisterDTO;
import com.example.xml.model.User;
import com.example.xml.repository.PacientRepository;

@Service
public class PatientService  {
	public final static String userId = "http://www.health_care.com/user";
	@Autowired
	PacientRepository pacientRepository;

	public void save(User pacient) {
		try {
			pacientRepository.save(pacient);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public User findById(String id) {
		try {
			return pacientRepository.findPacientById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public User findByUsername(String username) {
		try {
			return pacientRepository.findByUsername(username);
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
