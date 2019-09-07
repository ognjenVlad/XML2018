package com.example.xml.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;

import com.example.xml.dtos.PrescriptionDTO;
import com.example.xml.dtos.RecordDTO;
import com.example.xml.dtos.ReferralDTO;
import com.example.xml.dtos.RegisterDTO;
import com.example.xml.dtos.ReportDTO;
import com.example.xml.model.patient.Patient;
import com.example.xml.model.user.User;
import com.example.xml.service.DocumentsService;
import com.example.xml.service.PatientService;
import com.example.xml.service.RecordService;
import com.example.xml.service.ReportService;
import com.example.xml.service.TechnicianService;
import com.itextpdf.text.DocumentException;
import com.example.xml.model.record.Record;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.io.File;

@RestController
@RequestMapping(value = "record")
public class RecordController {

	@Autowired
	RecordService recordService;
	
	@Autowired
	ReportService reportService;
	
	@Autowired
	DocumentsService documentsService;
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	TechnicianService technicianService;
	
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Record> getRecordById(@RequestParam String id) throws Exception{
        Record record = recordService.findById(id);

        return new ResponseEntity<Record>(record, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/create",
			consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.POST)
	public ResponseEntity<Record> save(@RequestBody RecordDTO newRecord) {
		Record u = this.recordService.mapDtoToRecord(newRecord);
		Record oldRecord = this.recordService.findByPatientJmbg(newRecord.getPatientJmbg());
		if (oldRecord == null) {
			this.recordService.save(u);			
		} else {
			oldRecord.setDoctorId(newRecord.getDoctorId());
			this.recordService.save(oldRecord);	
		}
		return new ResponseEntity<Record>(u, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/create-report",
			consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.POST)
	public ResponseEntity<Record> createReport(@RequestBody RecordDTO newRecord) {
		ReportDTO report = newRecord.getReports().get(0);
		System.out.println("VREDNOST HTML-a::::::::" + report.getOpinion());
		report.setId(newRecord.getPatientJmbg() + "_" + report.getTime().hashCode());
		Record u = this.recordService.mapDtoToRecord(newRecord);
		
		Record oldRecord = this.recordService.findByPatientJmbg(newRecord.getPatientJmbg());

		if (newRecord.getReports().size() > 0) {
			this.reportService.save(newRecord);			
		}
		
		if (oldRecord == null) {
			this.recordService.save(u);			
		} else {
			oldRecord.getReportIds().add(u.getReportIds().get(0));
			System.out.println(oldRecord.getReportIds().get(0));
			System.out.println("NOVI" + oldRecord.getReportIds().size());
			this.recordService.save(oldRecord);	
		}
		
		return new ResponseEntity<Record>(u, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/create-prescription",
			consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.POST)
	public ResponseEntity<Record> createPrescription(@RequestBody RecordDTO newRecord) {
		PrescriptionDTO prescription = newRecord.getPrescriptions().get(0);
		prescription.setId(newRecord.getPatientJmbg() + "_" + prescription.getTime().hashCode());
		Record u = this.recordService.mapDtoToRecord(newRecord);
		
		Record oldRecord = this.recordService.findByPatientJmbg(newRecord.getPatientJmbg());

		if (newRecord.getPrescriptions().size() > 0) {
			this.documentsService.createPrescription(prescription);			
		}
		
		if (oldRecord == null) {
			this.recordService.save(u);			
		} else {
			List<String> oldList = oldRecord.getPrescriptionIds();
			oldList.add(u.getPrescriptionIds().get(0));
			this.recordService.save(oldRecord);	
		}
		
		return new ResponseEntity<Record>(u, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/create-referral",
			consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.POST)
	public ResponseEntity<Record> createReferral(@RequestBody RecordDTO newRecord) {
		ReferralDTO referral = newRecord.getReferrals().get(0);
		referral.setId(newRecord.getPatientJmbg() + "_" + referral.getTime().hashCode());
		Record u = this.recordService.mapDtoToRecord(newRecord);
		
		Record oldRecord = this.recordService.findByPatientJmbg(newRecord.getPatientJmbg());

		if (newRecord.getReferrals().size() > 0) {
			this.documentsService.createReferral(referral);			
		}
		
		if (oldRecord == null) {
			this.recordService.save(u);			
		} else {
			List<String> oldList = oldRecord.getReferralIds();
			oldList.add(u.getReferralIds().get(0));
			System.out.println(oldRecord.getReferralIds().get(0));
			System.out.println("NOVI" + oldRecord.getReferralIds().size());
			this.recordService.save(oldRecord);	
		}
		
		return new ResponseEntity<Record>(u, HttpStatus.OK);
    }
	
	@RequestMapping(path = "/download", method = RequestMethod.GET)
	public ResponseEntity<Resource> download(String id) throws IOException, DocumentException {
		Patient p = this.patientService.findById(id);
		System.out.println("LBO JE " + p.getLbo());
		Record r = this.recordService.findByPatientJmbg(p.getLbo());
		System.out.println("AJ NADJI GA" + r.getPatientJmbg());
		this.recordService.saveRecordToFile(r);
		File file = this.technicianService.generatePdf();
		System.out.println(file.getAbsolutePath());
	    Path path = Paths.get(file.getAbsolutePath());
	    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

	    return ResponseEntity.ok()
	    		.contentLength(file.length())
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}
}