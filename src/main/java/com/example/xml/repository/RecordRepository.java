package com.example.xml.repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import org.springframework.util.SerializationUtils;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.w3c.dom.Element;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
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
import com.example.xml.util.MetadataExtractor;
import com.example.xml.util.RdfUtilities;
import com.example.xml.util.SparqlUtil;

@Repository
public class RecordRepository {

	private static final String SPARQL_NAMED_GRAPH_URI = "/health_system/sparql/metadata";
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
	        deleteMetada(record.getId());
	        res.setContent(os);
	        System.out.println("[INFO] Storing the document: " + res.getId());
	        org.w3c.dom.Node n = res.getContentAsDOM().getFirstChild();
            ((Element)n).setAttribute("vocab", "http://www.health_care.com/rdf/hs/");
            ((Element)n).setAttribute("about", record.getId());
            for(int i = 0; i < n.getChildNodes().getLength(); i++){
                if(n.getChildNodes().item(i).getNodeName().contains("patient_lbo")){
                    ((org.w3c.dom.Element)n.getChildNodes().item(i)).setAttribute("property", "hs:lbo");
                    ((org.w3c.dom.Element)n.getChildNodes().item(i)).setAttribute("datatype", "xs:string");
                } else if(n.getChildNodes().item(i).getNodeName().contains("doctor_id")){
                    ((org.w3c.dom.Element)n.getChildNodes().item(i)).setAttribute("property", "hs:doctor");
                    ((org.w3c.dom.Element)n.getChildNodes().item(i)).setAttribute("datatype", "xs:string");
                }
            }
            res.setContentAsDOM(n.getParentNode());
	        col.storeResource(res);
	        System.out.println(res.getContent().toString());
	        addMetadata(res.getContent().toString());
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
	
	private static void addMetadata(Object content) throws Exception{
        RdfUtilities.ConnectionProperties connect = RdfUtilities.loadProperties();
        MetadataExtractor metadataExtractor = new MetadataExtractor();
        System.out.println("[INFO] Extracting metadata from RDFa attributes...");
        InputStream in = new ByteArrayInputStream(content.toString().getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        metadataExtractor.extractMetadata(in, out);

        // Loading a default model with extracted metadata
        Model model = ModelFactory.createDefaultModel();
        model.read(new ByteArrayInputStream(out.toByteArray()), "RDF");
        out = new ByteArrayOutputStream();
        model.write(out, SparqlUtil.NTRIPLES);

        System.out.println("[INFO] Extracted metadata as RDF/XML...");
        model.write(System.out, SparqlUtil.RDF_XML);
        model.write(System.out, SparqlUtil.NTRIPLES);
        model.write(out, SparqlUtil.NTRIPLES);
        // Writing the named graph
        String sparqlUpdate = SparqlUtil.insertData(connect.dataEndpoint + SPARQL_NAMED_GRAPH_URI, new String(out.toByteArray()));

        // UpdateRequest represents a unit of execution
        UpdateRequest update = UpdateFactory.create(sparqlUpdate);

        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, connect.updateEndpoint);
        processor.execute();
    }
	
	private static void deleteMetada(String id) throws Exception{
        RdfUtilities.ConnectionProperties connect = RdfUtilities.loadProperties();
        String delete = "<" + id + "> ?p ?o";
        String sparqlUpdate = SparqlUtil.deleteData(connect.dataEndpoint + SPARQL_NAMED_GRAPH_URI, delete);
        UpdateRequest update = UpdateFactory.create(sparqlUpdate);
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(update, connect.updateEndpoint);
        processor.execute();
    }

}
