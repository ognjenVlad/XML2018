package com.example.xml.dtos;

public class PrescriptionDTO {
	 
	private String patientJmbg;
	
    private String doctorId;
    
    private String time;
    
    private String date;
    
    private String id;

    private Object drug;
    
    public PrescriptionDTO() {}
    
	public PrescriptionDTO(String patientJmbg, String doctorId, String time, String date, String id,
			String drug) {
		super();
		this.patientJmbg = patientJmbg;
		this.doctorId = doctorId;
		this.time = time;
		this.id = id;
		this.date = date;
		this.drug = drug;
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

	public Object getDrug() {
		return drug;
	}

	public void setDrug(Object opinion) {
		this.drug = opinion;
	}

	@Override
	public String toString() {
		return "ReportDTO [patientJmbg=" + patientJmbg + ", doctorId=" + doctorId + ", time=" + time + ", date=" + date
				+ ", id=" + id + ", opinion=" + drug + "]";
	}
}
