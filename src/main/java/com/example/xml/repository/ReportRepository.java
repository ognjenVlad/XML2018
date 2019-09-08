package com.example.xml.repository;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
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

import com.example.xml.model.referral.Referral;
import com.example.xml.model.report.Report;
import com.example.xml.model.report.ReportPDF;
import com.example.xml.util.AuthenticationUtilities;
import com.example.xml.util.ConnectUtil;

@Repository
public class ReportRepository {
	private static final String TARGET_NAMESPACE = "http://www.health_care.com/report";
	@Autowired
    private ConnectUtil connectUtil;
	
	
	public XMLResource save(Report report) throws  Exception{
		Database database = this.connectUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
        DatabaseManager.registerDatabase(database);
        String collectionId = "/db/health_care_system/reports";
        String documentId = report.getId();
        Collection col = null;
        XMLResource res = null;
        OutputStream os = new ByteArrayOutputStream();
        try {
	        col = ConnectUtil.getOrCreateCollection(collectionId, 0, AuthenticationUtilities.loadProperties());
	        res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);
	        JAXBContext context = JAXBContext.newInstance("com.example.xml.model.report");

	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        XMLWriter writer = new XMLWriter(os);
	        writer.setEscapeText(false); // <----------------- this line
	        // Attach the writer to the filter
	        String path = new ClassPathResource("schema/report.xsd").getFile().getPath();
	        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, path);
	        marshaller.marshal(report, writer);
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
	
	public List<ReportPDF> getReportsByJmbg(String jmbg) throws Exception {
		Database database = this.connectUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
        DatabaseManager.registerDatabase(database);
    	String collectionId = "/db/health_care_system/reports";
    	Collection col = ConnectUtil.getOrCreateCollection( collectionId, 0, AuthenticationUtilities.loadProperties());
        XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
        xpathService.setProperty("indent", "yes");
        xpathService.setNamespace("", TARGET_NAMESPACE);
        String xpathExp = "//report/*[contains(local-name(),'patient_jmbg')][.=\"" + jmbg + "\"]/..";
		ResourceSet result = xpathService.query(xpathExp);
        ResourceIterator i = result.getIterator();
        Resource next = null;
        List<ReportPDF> reports = new ArrayList<ReportPDF>();
        while(i.hasMoreResources()) {
            try {
                next = i.nextResource();
                JAXBContext context = JAXBContext.newInstance("com.example.xml.model.report");
                Unmarshaller unmarshaller = context.createUnmarshaller();
                System.out.println("REPORT::" + ((XMLResource)next).getContentAsDOM());
                reports.add((ReportPDF) unmarshaller.unmarshal(((XMLResource)next).getContentAsDOM()));
            } finally {
                try {
                    ((EXistResource)next).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
            
        }
        return reports;
	}
}
