package com.example.xml.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xml.model.User;
import com.example.xml.repository.PacientRepository;

@Service
public class PacientService {
	
	@Autowired
	PacientRepository pacientRepository;

	public User save(User pacient) {
		try {
			return (User) pacientRepository.save(pacient);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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
}
