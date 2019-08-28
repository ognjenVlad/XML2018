package com.example.xml.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class RecordDTO implements Serializable {

    @NotNull
    private String patientLbo;
    @NotNull
    private String doctorId;
    
    public RecordDTO() { }

    public RecordDTO(String patientLbo, String doctorId) {
        this.patientLbo = patientLbo;
        this.doctorId = doctorId;
    }

	public String getPatientLbo() {
		return patientLbo;
	}

	public void setPatientLbo(String patientLbo) {
		this.patientLbo = patientLbo;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

}