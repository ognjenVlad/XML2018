package com.example.xml.service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.w3c.dom.Node;
import com.example.xml.model.record.Record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xml.dtos.RecordDTO;
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
	
	public Record findById(String id) {
		try {
			return recordRepository.findRecordById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Record findByPatientLbo(String lbo) {
		try {
			return recordRepository.findByPatientLbo(lbo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Record mapDtoToRecord(RecordDTO dto) {
		System.out.println(dto);
		Record record = new Record();
		record.setDoctorId(dto.getDoctorId());
		record.setPatientLbo(dto.getPatientLbo());
		record.setId(recordId + dto.getPatientLbo());
		return record;
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
