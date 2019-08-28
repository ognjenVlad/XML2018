package com.example.xml.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xml.dtos.RecordDTO;
import com.example.xml.dtos.RegisterDTO;
import com.example.xml.model.Patient;
import com.example.xml.model.Record;
import com.example.xml.model.User;
import com.example.xml.repository.RecordRepository;
import com.example.xml.util.Roles;

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
	
	public Record findById(String id) {
		try {
			return recordRepository.findRecordById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Record findByUsername(String lbo) {
		try {
			return recordRepository.findByPatientLbo(lbo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Record mapDtoToRecord(RecordDTO dto) {
		Record record = new Record();
		record.setDoctorId(dto.getDoctorId());
		record.setPatientLbo(dto.getPatientLbo());
		record.setId(recordId + dto.getPatientLbo());
		return record;
	}
}
