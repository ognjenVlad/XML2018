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
import com.example.xml.dtos.RecordDTO;
import com.example.xml.dtos.RegisterDTO;
import com.example.xml.model.patient.Patient;
import com.example.xml.model.user.User;
import com.example.xml.service.PatientService;
import com.example.xml.service.RecordService;
import com.example.xml.service.TechnicianService;
import com.itextpdf.text.DocumentException;
import com.example.xml.model.record.Record;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

@RestController
@RequestMapping(value = "record")
public class RecordController {

	@Autowired
	RecordService recordService;
	
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
		this.recordService.save(u);
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