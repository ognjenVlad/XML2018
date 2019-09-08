package com.example.xml.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xml.dtos.RegisterDTO;
import com.example.xml.model.patient.Patient;
import com.example.xml.model.user.User;
import com.example.xml.repository.TechnicianRepository;
import com.example.xml.util.PDFTransformer;
import com.example.xml.util.Roles;
import com.itextpdf.text.DocumentException;

@Service
public class TechnicianService {
	public static final String OUTPUT_FILE = "gen/output.pdf";

	public static final String INPUT_FILE = "data/temp.xml";
	public static final String XSL_FILE = "src/main/resources/xsl/record-full.xsl";
	
	public static final String XSL_FILE_ANON = "src/main/resources/xsl/record-anon.xsl";
	
	public final static String userId = "http://www.health_care.com/technician/";
	@Autowired
	TechnicianRepository technicianRepository;
	
	public void save(User technician) {
		try {
			technician.setRole(Roles.TECHNICIAN.toString());
			technicianRepository.save(technician);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public User findById(String id) {
		try {
			return technicianRepository.findPacientById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public User findByUsername(String username) {
		try {
			return technicianRepository.findByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public User mapDtoToUser(RegisterDTO dto) {
		User user = new User();
		User.Name name = new User.Name();
		name.setValue(dto.getName());
		user.setName(name);
		user.setJmbg(dto.getJmbg());
		user.setLastname(dto.getLastname());
		user.setPassword(dto.getPassword());
		user.setUsername(dto.getUsername());
		user.setId(userId + dto.getJmbg());
		return user;
	}
	
	public File generatePdf() throws IOException, DocumentException {

    	System.out.println("[INFO] " + PDFTransformer.class.getSimpleName());
    	
    	File pdfFile = new File(OUTPUT_FILE);
    	
		if (!pdfFile.getParentFile().exists()) {
			System.out.println("[INFO] A new directory is created: " + pdfFile.getParentFile().getAbsolutePath() + ".");
			pdfFile.getParentFile().mkdir();
		}
    	
		PDFTransformer pdfTransformer = new PDFTransformer();
		
		pdfTransformer.generateHTML(INPUT_FILE, XSL_FILE);
		pdfTransformer.generatePDF(OUTPUT_FILE);
		
		System.out.println("[INFO] From file \"" + XSL_FILE + "\" xsl.");
		System.out.println("[INFO] File \"" + OUTPUT_FILE + "\" generated successfully.");
		System.out.println("[INFO] End.");
		
		return pdfFile;
    
	}
	
	public File generatePdfAnon() throws IOException, DocumentException {

    	System.out.println("[INFO] " + PDFTransformer.class.getSimpleName());
    	
    	File pdfFile = new File(OUTPUT_FILE);
    	
		if (!pdfFile.getParentFile().exists()) {
			System.out.println("[INFO] A new directory is created: " + pdfFile.getParentFile().getAbsolutePath() + ".");
			pdfFile.getParentFile().mkdir();
		}
    	
		PDFTransformer pdfTransformer = new PDFTransformer();
		
		pdfTransformer.generateHTML(INPUT_FILE, XSL_FILE_ANON);
		pdfTransformer.generatePDF(OUTPUT_FILE);
		
		System.out.println("[INFO] File \"" + OUTPUT_FILE + "\" generated successfully.");
		System.out.println("[INFO] End.");
		
		return pdfFile;
    
	}
}
