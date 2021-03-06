package com.example.xml.dtos;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.example.xml.model.report.Report;

public class RecordDTO implements Serializable {

    @NotNull
    private String patientJmbg;
    @NotNull
    private String doctorId;
    private List<ReportDTO> reports;
    private List<ReferralDTO> referrals;
    private List<PrescriptionDTO> prescriptions
    ;
    
    public RecordDTO() { }

    public RecordDTO(String patientJmbg, String doctorId, List<ReportDTO> reports) {
        this.patientJmbg = patientJmbg;
        this.doctorId = doctorId;
        this.reports = reports;
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
	
	public List<ReportDTO> getReports() {
		return reports;
	}

	public void setReports(List<ReportDTO> reports) {
		this.reports = reports;
	}

	public List<ReferralDTO> getReferrals() {
		return referrals;
	}

	public void setReferrals(List<ReferralDTO> referrals) {
		this.referrals = referrals;
	}

	public List<PrescriptionDTO> getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(List<PrescriptionDTO> prescriptions) {
		this.prescriptions = prescriptions;
	}

	@Override
	public String toString() {
		return "RecordDTO [patientJmbg=" + patientJmbg + ", doctorId=" + doctorId + ", reports=" + reports + "]";
	}

}