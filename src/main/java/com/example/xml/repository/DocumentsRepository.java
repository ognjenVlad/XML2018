package com.example.xml.repository;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.dom4j.io.XMLWriter;
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

import com.example.xml.dtos.PrescriptionDTO;
import com.example.xml.dtos.ReferralDTO;
import com.example.xml.model.prescription.Prescription;
import com.example.xml.model.record.Record;
import com.example.xml.model.referral.Referral;
import com.example.xml.model.report.Report;
import com.example.xml.util.AuthenticationUtilities;
import com.example.xml.util.ConnectUtil;

@Repository
public class DocumentsRepository {
	private static final String TARGET_NAMESPACE_REF = "http://www.health_care.com/referral";
	private static final String TARGET_NAMESPACE_PRE = "http://www.health_care.com/prescription";
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
	
	public List<Referral> getRefferalsByJmbg(String jmbg) throws Exception {
		Database database = this.connectUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
        DatabaseManager.registerDatabase(database);
    	String collectionId = "/db/health_care_system/referral";
    	Collection col = ConnectUtil.getOrCreateCollection( collectionId, 0, AuthenticationUtilities.loadProperties());
        XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
        xpathService.setProperty("indent", "yes");
        xpathService.setNamespace("", TARGET_NAMESPACE_REF);
        String xpathExp = "//referral/*[contains(local-name(),'patient_jmbg')][.=\"" + jmbg + "\"]/..";
		ResourceSet result = xpathService.query(xpathExp);
        ResourceIterator i = result.getIterator();
        Resource next = null;
        List<Referral> referral = new ArrayList<Referral>();
        while(i.hasMoreResources()) {
            try {
                next = i.nextResource();
                JAXBContext context = JAXBContext.newInstance("com.example.xml.model.referral");
                Unmarshaller unmarshaller = context.createUnmarshaller();
                System.out.println("REFERAL::" + ((XMLResource)next).getContentAsDOM());
                System.out.println(((Referral) unmarshaller.unmarshal(((XMLResource)next).getContentAsDOM())).getToDoctorId());
                referral.add((Referral) unmarshaller.unmarshal(((XMLResource)next).getContentAsDOM()));
            } finally {
                try {
                    ((EXistResource)next).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
            
        }
        return referral;
	}
	
	public List<Prescription> getPrecscriptionsByJmbg(String jmbg) throws Exception{
		Database database = this.connectUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
        DatabaseManager.registerDatabase(database);
    	String collectionId = "/db/health_care_system/prescriptions";
    	Collection col = ConnectUtil.getOrCreateCollection( collectionId, 0, AuthenticationUtilities.loadProperties());
        XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
        xpathService.setProperty("indent", "yes");
        xpathService.setNamespace("", TARGET_NAMESPACE_PRE);
        String xpathExp = "//prescription/*[contains(local-name(),'patient_jmbg')][.=\"" + jmbg + "\"]/..";
		ResourceSet result = xpathService.query(xpathExp);
        ResourceIterator i = result.getIterator();
        Resource next = null;
        List<Prescription> prescription = new ArrayList<Prescription>();
        while(i.hasMoreResources()) {
            try {
                next = i.nextResource();
                JAXBContext context = JAXBContext.newInstance("com.example.xml.model.prescription");
                Unmarshaller unmarshaller = context.createUnmarshaller();
                prescription.add((Prescription) unmarshaller.unmarshal(((XMLResource)next).getContentAsDOM()));
            } finally {
                try {
                    ((EXistResource)next).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
            
        }
        return prescription;
	}
}
