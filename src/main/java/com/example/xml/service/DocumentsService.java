package com.example.xml.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xml.dtos.PrescriptionDTO;
import com.example.xml.dtos.RecordDTO;
import com.example.xml.dtos.ReferralDTO;
import com.example.xml.dtos.ReportDTO;
import com.example.xml.model.prescription.Prescription;
import com.example.xml.model.prescription.PrescriptionPDF;
import com.example.xml.model.record.Record;
import com.example.xml.model.referral.Referral;
import com.example.xml.model.referral.ReferralPDF;
import com.example.xml.repository.DocumentsRepository;


@Service
public class DocumentsService {
	@Autowired
	DocumentsRepository documentsRepository;

	public void createPrescription(PrescriptionDTO p) {
		try {
			documentsRepository.savePrescription(this.mapPrescription(p));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createReferral(ReferralDTO p) {
		try {
			
			documentsRepository.saveReferral(this.mapReferral(p));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<ReferralPDF> getRefferalsByJmbg(String jmbg) {
		try {
			return documentsRepository.getRefferalsByJmbg(jmbg);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<PrescriptionPDF> getPrecscriptionsByJmbg(String jmbg) {
		try {
			
			return documentsRepository.getPrecscriptionsByJmbg(jmbg);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public Prescription mapPrescription(PrescriptionDTO dto) {
		Prescription prescription = new Prescription();
		prescription.setDoctorId(dto.getDoctorId());
		prescription.setPatientJmbg(dto.getPatientJmbg());
		prescription.setId(dto.getId());
		prescription.setDate(dto.getDate());
		prescription.setTime(dto.getTime());
		prescription.setDrug(dto.getDrug());
		return prescription;
	}
	
	public Referral mapReferral(ReferralDTO dto) {
		Referral referral = new Referral();
		referral.setDoctorId(dto.getDoctorId());
		referral.setPatientJmbg(dto.getPatientJmbg());
		referral.setId(dto.getId());
		referral.setDate(dto.getDate());
		referral.setTime(dto.getTime());
		referral.setToDoctorId(dto.getToDoctorId());
		return referral;
	}
	
}
