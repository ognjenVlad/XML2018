package com.example.xml.controller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.xml.model.User;
import com.example.xml.service.PacientService;

@RestController
@RequestMapping(value = "pacient")
public class PacientController {

	@Autowired
	PacientService pacientService;
	
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<User> getPacijent() throws Exception{
		System.out.println("aaaaa");
        JAXBContext context = JAXBContext.newInstance(com.example.xml.model.User.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        User pacijent = (User) unmarshaller.unmarshal(new ClassPathResource("schema/user.xml").getFile());
        System.out.println(pacijent.toString());
        pacientService.save(pacijent);
        pacijent = pacientService.findById("2305979265742");

        return new ResponseEntity<User>(pacijent, HttpStatus.OK);
    }
}
