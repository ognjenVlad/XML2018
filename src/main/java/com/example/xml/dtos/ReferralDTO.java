package com.example.xml.dtos;

public class ReferralDTO {
	 
	private String patientJmbg;
	
    private String doctorId;
    
    private String toDoctorId;
    
    private String time;
    
    private String date;
    
    private String id;

    public ReferralDTO() {}
    
	public ReferralDTO(String patientJmbg, String doctorId, String time, String date, String id,
			String toDoctorId) {
		super();
		this.patientJmbg = patientJmbg;
		this.doctorId = doctorId;
		this.time = time;
		this.id = id;
		this.date = date;
		this.toDoctorId = toDoctorId;
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

	public void setDate(String date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToDoctorId() {
		return toDoctorId;
	}

	public void setYoDoctorId(String toDoctorId) {
		this.toDoctorId = toDoctorId;
	}

	@Override
	public String toString() {
		return "ReportDTO [patientJmbg=" + patientJmbg + ", doctorId=" + doctorId + ", time=" + time + ", date=" + date
				+ ", id=" + id + ", toDoctorId=" + toDoctorId + "]";
	}
}
