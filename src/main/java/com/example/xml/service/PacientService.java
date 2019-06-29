package com.example.xml.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xml.model.User;
import com.example.xml.repository.PacientRepository;

@Service
public class PacientService {
	
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
}
