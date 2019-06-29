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

import com.example.xml.model.User;
import com.example.xml.util.AuthenticationUtilities;
import com.example.xml.util.ConnectUtil;
import com.example.xml.util.DBData;

import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.DatabaseManager;


@Repository
public class PacientRepository {

	
	@Autowired
    private ConnectUtil connectUtil;
	
	private static AuthenticationUtilities.ConnectionProperties conn;
	
	
    public User findPacientById(String id) throws  Exception{
    	 String collectionId = "/db/zdravstveni_sistem/pacijenti";
         String documentId = id.trim();
         DBData data = this.connectUtil.getReourceById( collectionId, documentId, AuthenticationUtilities.loadProperties());
         JAXBContext context = JAXBContext.newInstance("com.example.xml.model");
         Unmarshaller unmarshaller = context.createUnmarshaller();
         User pacient = (User) unmarshaller.unmarshal(data.getResource().getContentAsDOM());
         return pacient;
    }
    
    public XMLResource save(User pacient) throws  Exception{
    	Database database = this.connectUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
        DatabaseManager.registerDatabase(database);
        String collectionId = "/db/health_care_system/pacients";
        String documentId = pacient.getId();
        // zapoceto
        System.out.println(documentId);
        Collection col = null;
        XMLResource res = null;
        OutputStream os = new ByteArrayOutputStream();
        try {
	        col = ConnectUtil.getOrCreateCollection(collectionId, 0, AuthenticationUtilities.loadProperties());
	        res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);
	        JAXBContext context = JAXBContext.newInstance("com.example.xml.model");
	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        String path = new ClassPathResource("schema/user.xsd").getFile().getPath();
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
