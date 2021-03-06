package com.example.xml.dtos;

import javax.xml.datatype.XMLGregorianCalendar;

public class AppointmentDTO {
	private String patientJmbg;
    
    private String doctorId;
    
    private String time;
    
    private String date;
    
    private String id;

    private Boolean isConfirmed;

    public AppointmentDTO() {}
    
	public AppointmentDTO(String patientJmbg, String doctorId, String time, String date, String id,
			Boolean isConfirmed) {
		super();
		this.patientJmbg = patientJmbg;
		this.doctorId = doctorId;
		this.time = time;
		this.id = id;
		this.isConfirmed = isConfirmed;
		this.date = date;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String time) {
		this.date = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getIsConfirmed() {
		return isConfirmed;
	}

	public void setIsConfirmed(Boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
    
    

}
