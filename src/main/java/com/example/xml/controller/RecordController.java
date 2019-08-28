package com.example.xml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.xml.dtos.RecordDTO;
import com.example.xml.dtos.RegisterDTO;
import com.example.xml.model.Patient;
import com.example.xml.model.Record;
import com.example.xml.model.User;
import com.example.xml.service.PatientService;
import com.example.xml.service.RecordService;

@RestController
@RequestMapping(value = "record")
public class RecordController {

	@Autowired
	RecordService recordService;
	
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Record> getPacijent(@RequestParam String id) throws Exception{
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
}
