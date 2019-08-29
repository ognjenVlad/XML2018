package com.example.xml.repository;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBIntrospector;
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

import com.example.xml.model.Patient;
import com.example.xml.model.Record;
import com.example.xml.util.AuthenticationUtilities;
import com.example.xml.util.ConnectUtil;
import com.example.xml.util.DBData;

@Repository
public class RecordRepository {

	@Autowired
    private ConnectUtil connectUtil;
	
	public Record findByPatientLbo(String lbo) throws  Exception{
    	Database database = this.connectUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
        DatabaseManager.registerDatabase(database);
    	String collectionId = "/db/health_care_system/records";
    	Collection col = ConnectUtil.getOrCreateCollection( collectionId, 0, AuthenticationUtilities.loadProperties());
        XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
        xpathService.setProperty("indent", "yes");
        String xpathExp = "//record/*[contains(local-name(),'patient_lbo')][.=\"" + lbo + "\"]/..";
		ResourceSet result = xpathService.query(xpathExp);
        ResourceIterator i = result.getIterator();
        Resource next = null;
        Record record = null;
        while(i.hasMoreResources()) {
            try {
                next = i.nextResource();
                JAXBContext context = JAXBContext.newInstance("com.example.xml.model");
                Unmarshaller unmarshaller = context.createUnmarshaller();
                record = (Record) unmarshaller.unmarshal(((XMLResource)next).getContentAsDOM());
            } finally {
                try {
                    ((EXistResource)next).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
            
        }
        return record;
    }
	
	public Record findRecordById(String id) throws  Exception{
   	 String collectionId = "/db/health_care_system/records";
        String documentId = id.trim();
		DBData data = this.connectUtil.getReourceById( collectionId, documentId, AuthenticationUtilities.loadProperties());
        JAXBContext context = JAXBContext.newInstance("com.example.xml.model");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Record record = (Record) JAXBIntrospector.getValue(unmarshaller.unmarshal(data.getResource().getContentAsDOM()));
        return record;
   }
	
	public XMLResource save(Record record) throws  Exception{
    	Database database = this.connectUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
        DatabaseManager.registerDatabase(database);
        String collectionId = "/db/health_care_system/records";
        String documentId = record.getPatientLbo();
        Collection col = null;
        XMLResource res = null;
        OutputStream os = new ByteArrayOutputStream();
        try {
	        col = ConnectUtil.getOrCreateCollection(collectionId, 0, AuthenticationUtilities.loadProperties());
	        res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);
	        JAXBContext context = JAXBContext.newInstance("com.example.xml.model");
	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        String path = new ClassPathResource("schema/record.xsd").getFile().getPath();
	        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, path);
	        marshaller.marshal(record, os);
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
