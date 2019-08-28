package com.example.xml.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class DoctorDTO implements Serializable {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @NotNull
    private String lastname;
    @NotNull
    private String jmbg;
    @NotNull
    private String specialty;

    public DoctorDTO() { }

    public DoctorDTO(String username, String password,String name, String lastname, String jmbg, String specialty) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.jmbg = jmbg;
        this.specialty = specialty;
    }

    public String getUsername() { return username; }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
    
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public String getSpecialty() { return specialty; }


    @Override
    public String toString() {
        return "LoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}