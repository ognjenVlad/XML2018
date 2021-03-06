package com.example.xml.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.xml.model.user.User;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	PatientService patientService;
	
	@Autowired
	TechnicianService technicianService;
	
	@Autowired
	DoctorServiceImpl doctorService;
	
	@Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u = this.patientService.findByUsername(username);
		System.out.println("user::::::::"+u);
		if (u == null) {
			u = this.technicianService.findByUsername(username);
		}
		
		if (u == null) {
			u = this.doctorService.findByUsername(username);
		}
		
		if (u == null) {
            throw new UsernameNotFoundException(String.format("There is no account with username '%s'.", username));
        } else {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(u.getRole()));
            return new org.springframework.security.core.userdetails.User(
                    u.getUsername(),
                    u.getPassword(),
                    grantedAuthorities);
    	}
	}
}
