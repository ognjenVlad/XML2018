package com.example.xml.repository;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;

import com.example.xml.model.Appointment;
import com.example.xml.model.Doctor;
import com.example.xml.model.Patient;
import com.example.xml.util.AuthenticationUtilities;
import com.example.xml.util.ConnectUtil;

@Repository
public class AppointmentsRepository {

	@Autowired
    private ConnectUtil connectUtil;
	
	private static AuthenticationUtilities.ConnectionProperties conn;
	
	public XMLResource save(Appointment appointment) throws  Exception{
		System.out.println(appointment.getTime());
		Database database = this.connectUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
        DatabaseManager.registerDatabase(database);
        String collectionId = "/db/health_care_system/appointments";
        String documentId = appointment.getDoctorId() + "/" + appointment.getPatientLbo();
        Collection col = null;
        XMLResource res = null;
        OutputStream os = new ByteArrayOutputStream();
        try {
	        col = ConnectUtil.getOrCreateCollection(collectionId, 0, AuthenticationUtilities.loadProperties());
	        res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);
	        JAXBContext context = JAXBContext.newInstance("com.example.xml.model");
	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        String path = new ClassPathResource("schema/appointment.xsd").getFile().getPath();
	        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, path);
	        marshaller.marshal(appointment, os);
	        res.setContent(os);
	        col.storeResource(res);
        return res;
        
        } finally {
	        if(res != null) {
	            try {
	                ((EXistResource)res).freeResources();
	            } catch (XMLDBException xe) {
	                xe.printStackTrace();
	            }
	        }
	        if(col != null) {
	            try {
	                col.close();
	            } catch (XMLDBException xe) {
	                xe.printStackTrace();
	            }
	        }
        }
    }
	
	public ArrayList<Appointment> allAppointmentsForDoc(String doctor_id) throws  Exception{		
		Database database = this.connectUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
		DatabaseManager.registerDatabase(database);
		String collectionId = "/db/health_care_system/appointments";
		Collection col = ConnectUtil.getOrCreateCollection( collectionId, 0, AuthenticationUtilities.loadProperties());
		XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
        xpathService.setProperty("indent", "yes");
        String xpathExp = "//appointment/*[contains(local-name(),'doctor_id')][.=\"" + doctor_id + "\"]/..";
		ResourceSet result = xpathService.query(xpathExp);
        ResourceIterator i = result.getIterator();
        Resource next = null;
        ArrayList<Appointment> doctors = new ArrayList<Appointment>();
        while(i.hasMoreResources()) {
            try {
                next = i.nextResource();
                System.out.println(((XMLResource)next).getContentAsDOM());
                JAXBContext context = JAXBContext.newInstance("com.example.xml.model");
                Unmarshaller unmarshaller = context.createUnmarshaller();
                Appointment d = (Appointment) unmarshaller.unmarshal(((XMLResource)next).getContentAsDOM());
                doctors.add(d);
            } finally {
                try {
                    ((EXistResource)next).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
            
        }
        return doctors;
	}
}
