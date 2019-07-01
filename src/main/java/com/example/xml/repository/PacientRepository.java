package com.example.xml.repository;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import com.example.xml.model.Patient;
import com.example.xml.model.User;
import com.example.xml.util.AuthenticationUtilities;
import com.example.xml.util.ConnectUtil;
import com.example.xml.util.DBData;

import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.modules.XPathQueryService;


@Repository
public class PacientRepository {

	
	@Autowired
    private ConnectUtil connectUtil;
	
	private static AuthenticationUtilities.ConnectionProperties conn;
	
	
    public Patient findPacientById(String id) throws  Exception{
    	 String collectionId = "/db/health_care_system/pacients";
         String documentId = id.trim();
         DBData data = this.connectUtil.getReourceById( collectionId, documentId, AuthenticationUtilities.loadProperties());
         JAXBContext context = JAXBContext.newInstance("com.example.xml.model");
         Unmarshaller unmarshaller = context.createUnmarshaller();
         Patient pacient = (Patient) unmarshaller.unmarshal(data.getResource().getContentAsDOM());
         return pacient;
    }
    
    public Patient findByUsername(String username) throws  Exception{
    	Database database = this.connectUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
        DatabaseManager.registerDatabase(database);
    	String collectionId = "/db/health_care_system/pacients";
    	Collection col = ConnectUtil.getOrCreateCollection( collectionId, 0, AuthenticationUtilities.loadProperties());
        XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
        xpathService.setProperty("indent", "yes");
        xpathService.setNamespace("", "http://www.health_care.com/patient");
        String xpathExp = "//user/*[contains(local-name(),'username')][.=\"" + username + "\"]/..";
		ResourceSet result = xpathService.query(xpathExp);
        ResourceIterator i = result.getIterator();
        Resource next = null;
        Patient user = null;
        while(i.hasMoreResources()) {
            try {
                next = i.nextResource();
                JAXBContext context = JAXBContext.newInstance("com.example.xml.model");
                Unmarshaller unmarshaller = context.createUnmarshaller();
                user = (Patient) unmarshaller.unmarshal(((XMLResource)next).getContentAsDOM());
            } finally {
                try {
                    ((EXistResource)next).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
            
        }
        return user;
    }
    
    public XMLResource save(Patient pacient) throws  Exception{
    	Database database = this.connectUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
        DatabaseManager.registerDatabase(database);
        String collectionId = "/db/health_care_system/pacients";
        String documentId = pacient.getJmbg();
        Collection col = null;
        XMLResource res = null;
        OutputStream os = new ByteArrayOutputStream();
        try {
	        col = ConnectUtil.getOrCreateCollection(collectionId, 0, AuthenticationUtilities.loadProperties());
	        res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);
	        JAXBContext context = JAXBContext.newInstance("com.example.xml.model");
	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        String path = new ClassPathResource("schema/patient.xsd").getFile().getPath();
	        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, path);
	        marshaller.marshal(pacient, os);
	        res.setContent(os);
	        System.out.println("[INFO] Storing the document: " + res.getId());
	        
	        col.storeResource(res);
	        System.out.println("[INFO] Done.");
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
