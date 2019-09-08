package com.example.xml.service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;

import com.example.xml.model.patient.Patient;
import com.example.xml.model.prescription.Prescription;
import com.example.xml.model.prescription.PrescriptionPDF;
import com.example.xml.model.record.Record;
import com.example.xml.model.recordFull.RecordFull;
import com.example.xml.model.referral.Referral;
import com.example.xml.model.referral.ReferralPDF;
import com.example.xml.model.report.Report;
import com.example.xml.model.report.ReportPDF;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xml.dtos.PrescriptionDTO;
import com.example.xml.dtos.RecordDTO;
import com.example.xml.dtos.ReferralDTO;
import com.example.xml.dtos.ReportDTO;
import com.example.xml.repository.RecordRepository;

@Service
public class RecordService {
	public final static String recordId = "http://www.health_care.com/record/";
	@Autowired
	RecordRepository recordRepository;

	public void save(Record record) {
		try {
			recordRepository.save(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveRecordToFile(Record record) {
		try {
			recordRepository.saveRecordToFile(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveFullRecordToFile(RecordFull record) {
		try {
			recordRepository.saveFullRecordToFile(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Record findById(String id) {
		try {
			return recordRepository.findRecordById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Record findByPatientJmbg(String jmbg) {
		try {
			return recordRepository.findByPatientJmbg(jmbg);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Record mapDtoToRecord(RecordDTO dto) {
		System.out.println(dto);
		Record record = new Record();
		record.setDoctorId(dto.getDoctorId());
		record.setPatientJmbg(dto.getPatientJmbg());
		record.setId(recordId + dto.getPatientJmbg());
		List<String> reportIds = new ArrayList<String>();
		
		if (dto.getReports() != null) {			
			for(ReportDTO r: dto.getReports()) {
				System.out.println("ID:" +r.getId() );
				reportIds.add(r.getId());
			}
			record.setReportIds(reportIds);
		}
		
		if (dto.getPrescriptions() != null) {			
			for(PrescriptionDTO r: dto.getPrescriptions()) {
				System.out.println("ID:" +r.getId() );
				record.getPrescriptionIds().add(r.getId());
			}
		}
		
		if (dto.getReferrals() != null) {			
			for(ReferralDTO r: dto.getReferrals()) {
				record.getReferralIds().add(r.getId());
			}
		}
		return record;
	}
	
	public RecordFull mapFullRecord(Record r, Patient p, List<ReferralPDF> referrals, List<PrescriptionPDF> prescriptions, List<ReportPDF> reports) {
		RecordFull newRecord = new RecordFull();
		newRecord.setPatientName(p.getName().getValue());
		newRecord.setPatientLbo(p.getLbo());
		newRecord.setPatientJmbg(p.getJmbg());
		newRecord.setPatientLastname(p.getLastname());
		newRecord.setReferrals(referrals);
		newRecord.setPrescriptions(prescriptions);
		newRecord.setReport(reports);
		return newRecord;
	}
	
	public List<Record> search(String term) {
		try {
			return recordRepository.simpleSearch(term);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> advancedSearch(String term, String criteria) {
		try {
			return recordRepository.advancedSearch(criteria, term);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
