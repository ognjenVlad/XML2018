package com.example.xml.repository;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.dom4j.io.XMLWriter;
import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.example.xml.dtos.PrescriptionDTO;
import com.example.xml.dtos.ReferralDTO;
import com.example.xml.model.prescription.Prescription;
import com.example.xml.model.referral.Referral;
import com.example.xml.model.report.Report;
import com.example.xml.util.AuthenticationUtilities;
import com.example.xml.util.ConnectUtil;

@Repository
public class DocumentsRepository {

	@Autowired
    private ConnectUtil connectUtil;
	
	
	public XMLResource savePrescription(Prescription prescription) throws  Exception{
		Database database = this.connectUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
        DatabaseManager.registerDatabase(database);
        String collectionId = "/db/health_care_system/prescriptions";
        String documentId = prescription.getId();
        Collection col = null;
        XMLResource res = null;
        OutputStream os = new ByteArrayOutputStream();
        try {
	        col = ConnectUtil.getOrCreateCollection(collectionId, 0, AuthenticationUtilities.loadProperties());
	        res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);
	        JAXBContext context = JAXBContext.newInstance("com.example.xml.model.prescription");

	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        XMLWriter writer = new XMLWriter(os);
	        writer.setEscapeText(false); // <----------------- this line
	        // Attach the writer to the filter
	        String path = new ClassPathResource("schema/prescription.xsd").getFile().getPath();
	        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, path);
	        marshaller.marshal(prescription, writer);
	        res.setContent(os);
	        System.out.println("VREDNOST:::::::::::" +res.getContent().toString());
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
	
	public XMLResource saveReferral(Referral referral) throws  Exception{
		Database database = this.connectUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
        DatabaseManager.registerDatabase(database);
        String collectionId = "/db/health_care_system/referral";
        String documentId = referral.getId();
        Collection col = null;
        XMLResource res = null;
        OutputStream os = new ByteArrayOutputStream();
        try {
	        col = ConnectUtil.getOrCreateCollection(collectionId, 0, AuthenticationUtilities.loadProperties());
	        res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);
	        JAXBContext context = JAXBContext.newInstance("com.example.xml.model.referral");

	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        XMLWriter writer = new XMLWriter(os);
	        writer.setEscapeText(false); // <----------------- this line
	        // Attach the writer to the filter
	        String path = new ClassPathResource("schema/referral.xsd").getFile().getPath();
	        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, path);
	        marshaller.marshal(referral, writer);
	        res.setContent(os);
	        System.out.println("VREDNOST:::::::::::" +res.getContent().toString());
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
}
