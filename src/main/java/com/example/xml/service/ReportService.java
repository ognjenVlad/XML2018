package com.example.xml.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xml.dtos.RecordDTO;
import com.example.xml.dtos.ReportDTO;
import com.example.xml.model.record.Record;
import com.example.xml.model.referral.Referral;
import com.example.xml.model.report.Report;
import com.example.xml.repository.ReportRepository;


@Service
public class ReportService {

	@Autowired
	ReportRepository reportRepository;
	
	public void save(RecordDTO record) {
		try {
			if (record.getReports().size() > 0) {
				Report r = this.mapDto(record.getReports().get(record.getReports().size()-1));
				reportRepository.save(r);				
			} else {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Report> getReportsByJmbg(String jmbg) {
		try {
			
			return reportRepository.getReportsByJmbg(jmbg);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Report mapDto(ReportDTO dto) {
		Report report = new Report();
		report.setId(dto.getId());
		if(dto.getDiagnosisId() == null) {
			report.setDiagnosisId(null);		
		} else {			
			report.setDiagnosisId(dto.getDiagnosisId().getId());
		}
		report.setDoctorId(dto.getDoctorId());
		report.setOpinion(dto.getOpinion());
		report.setPatientJmbg(dto.getPatientJmbg());
		report.setTime(dto.getTime());
		report.setDate(dto.getDate());
		return report;
	}
	
}
