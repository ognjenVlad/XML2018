package com.example.xml.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class RecordDTO implements Serializable {

    @NotNull
    private String patientJmbg;
    @NotNull
    private String doctorId;
    
    public RecordDTO() { }

    public RecordDTO(String patientJmbg, String doctorId) {
        this.patientJmbg = patientJmbg;
        this.doctorId = doctorId;
    }

	public String getPatientJmbg() {
		return patientJmbg;
	}

	public void setPatientJmbg(String patientJmbg) {
		this.patientJmbg = patientJmbg;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

}