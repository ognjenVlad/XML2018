package com.example.xml.repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.util.SerializationUtils;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.apache.commons.io.FileUtils;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
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

import com.example.xml.model.patient.Patient;
import com.example.xml.model.record.Record;
import com.example.xml.model.recordFull.RecordFull;
import com.example.xml.util.AuthenticationUtilities;
import com.example.xml.util.ConnectUtil;
import com.example.xml.util.DBData;
import com.example.xml.util.MetadataExtractor;
import com.example.xml.util.RdfUtilities;
import com.example.xml.util.SparqlUtil;

@Repository
public class RecordRepository {

	private static final String SPARQL_NAMED_GRAPH_URI = "/health_system/sparql/metadata";
	private static final String HEALTH_CARE_RDF_URI = "http://www.health_care.com/rdf/hs/";
	private static final String TARGET_NAMESPACE = "http://www.health_care.com/record";
	@Autowired
    private ConnectUtil connectUtil;
	
	public com.example.xml.model.record.Record findByPatientJmbg(String jmbg) throws  Exception{
    	Database database = this.connectUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
        DatabaseManager.registerDatabase(database);
    	String collectionId = "/db/health_care_system/records";
    	Collection col = ConnectUtil.getOrCreateCollection( collectionId, 0, AuthenticationUtilities.loadProperties());
        XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
        xpathService.setProperty("indent", "yes");
        xpathService.setNamespace("", TARGET_NAMESPACE);
        String xpathExp = "//record/*[contains(local-name(),'patient_jmbg')][.=\"" + jmbg + "\"]/..";
		ResourceSet result = xpathService.query(xpathExp);
        ResourceIterator i = result.getIterator();
        Resource next = null;
        Record record = null;
        while(i.hasMoreResources()) {
            try {
                next = i.nextResource();
                JAXBContext context = JAXBContext.newInstance("com.example.xml.model.record");
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
        JAXBContext context = JAXBContext.newInstance("com.example.xml.model.record");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Record record = (Record) JAXBIntrospector.getValue(unmarshaller.unmarshal(data.getResource().getContentAsDOM()));
        return record;
   }
	
	
	public XMLResource save(Record record) throws  Exception{
    	Database database = this.connectUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
        DatabaseManager.registerDatabase(database);
        String collectionId = "/db/health_care_system/records";
        String documentId = record.getPatientJmbg();
        Collection col = null;
        XMLResource res = null;
        OutputStream os = new ByteArrayOutputStream();
        try {
	        col = ConnectUtil.getOrCreateCollection(collectionId, 0, AuthenticationUtilities.loadProperties());
	        res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);
	        JAXBContext context = JAXBContext.newInstance("com.example.xml.model.record");
	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        String path = new ClassPathResource("schema/record.xsd").getFile().getPath();
	        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, path);
	        marshaller.marshal(record, os);
	        deleteMetada(record.getId());
	        res.setContent(os);
	        col.storeResource(res);

	        System.out.println("[INFO] Storing the document: " + res.getId());
	        org.w3c.dom.Node n = res.getContentAsDOM().getFirstChild();
            ((Element)n).setAttribute("vocab", "http://www.health_care.com/rdf/hs/");
            ((Element)n).setAttribute("about", record.getId());
            for(int i = 0; i < n.getChildNodes().getLength(); i++){
                if(n.getChildNodes().item(i).getNodeName().contains("patient_jmbg")){
                    ((org.w3c.dom.Element)n.getChildNodes().item(i)).setAttribute("property", "hs:jmbg");
                    ((org.w3c.dom.Element)n.getChildNodes().item(i)).setAttribute("datatype", "xs:string");
                } else if(n.getChildNodes().item(i).getNodeName().contains("doctor_id")){
                    ((org.w3c.dom.Element)n.getChildNodes().item(i)).setAttribute("property", "hs:doctor");
                    ((org.w3c.dom.Element)n.getChildNodes().item(i)).setAttribute("datatype", "xs:string");
                }
            }
            res.setContentAsDOM(n.getParentNode());
	        System.out.println(res.getContent().toString());
	        addMetadata(org.apache.commons.lang3.SerializationUtils.clone(res.getContent().toString()));
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
	
	public void saveRecordToFile(Record record) throws  Exception{
        try {
        	File file = new File("data/temp.xml");
	        JAXBContext context = JAXBContext.newInstance("com.example.xml.model.record");
	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        String path = new ClassPathResource("schema/record.xsd").getFile().getPath();
	        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, path);
	        marshaller.marshal(record, file);
	        marshaller.marshal(record, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
	
	public void saveFullRecordToFile(RecordFull record) throws  Exception{
        try {
        	ByteArrayOutputStream os = new ByteArrayOutputStream();
        	File file = new File("data/temp.xml");
	        JAXBContext context = JAXBContext.newInstance("com.example.xml.model.recordFull");
	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	        String path = new ClassPathResource("schema/record-full.xsd").getFile().getPath();
	        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, path);
	        XMLWriter writer = new XMLWriter(os);
	        writer.setEscapeText(false);
	        marshaller.marshal(record, writer);
	        os.writeTo(new FileOutputStream("data/temp.xml"));
	        marshaller.marshal(record, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
	
	private static void addMetadata(Object content) throws Exception{
        RdfUtilities.ConnectionProperties connect = RdfUtilities.loadProperties();
        MetadataExtractor metadataExtractor = new MetadataExtractor();
        System.out.println("[INFO] Extracting metadata from RDFa attributes...");
        InputStream in = new ByteArrayInputStream(content.toString().getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        metadataExtractor.extractMetadata(in, out);

        Model model = ModelFactory.createDefaultModel();
        model.read(new ByteArrayInputStream(out.toByteArray()), "RDF");
        out = new ByteArrayOutputStream();
        model.write(out, SparqlUtil.NTRIPLES);

        System.out.println("[INFO] Extracted metadata as RDF/XML...");
        model.write(System.out, SparqlUtil.RDF_XML);
        model.write(System.out, SparqlUtil.NTRIPLES);
        model.write(out, SparqlUtil.NTRIPLES);
        String sparqlUpdate = SparqlUtil.insertData(connect.dataEndpoint + SPARQL_NAMED_GRAPH_URI, new String(out.toByteArray()));

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

	public List<String> advancedSearch(String criteria, String object) throws Exception{
        String pred = "";
        if(criteria.equals("jmbg"))
        	pred = "<" + HEALTH_CARE_RDF_URI + "jmbg>";
        else if(criteria.equals("doctor"))
            pred = "<" + HEALTH_CARE_RDF_URI + "doctor>";

        RdfUtilities.ConnectionProperties connect = RdfUtilities.loadProperties();

        String select;
        select = "?s " + pred + " \"" + object + "\"";

        System.out.println("[INFO] Selecting the triples from the named graph \"" + SPARQL_NAMED_GRAPH_URI + "\".");
        String sparqlQuery = SparqlUtil.selectData(connect.dataEndpoint + SPARQL_NAMED_GRAPH_URI, select);

        org.apache.jena.query.QueryExecution query = QueryExecutionFactory.sparqlService(connect.queryEndpoint, sparqlQuery);

        org.apache.jena.query.ResultSet results = query.execSelect();
        String varName;
        org.apache.jena.rdf.model.RDFNode varValue;
        List<String> resultList = new ArrayList<>();
        while (results.hasNext()) {

            org.apache.jena.query.QuerySolution querySolution = results.next();
            Iterator<String> variableBindings = querySolution.varNames();
            while (variableBindings.hasNext()) {

                varName = variableBindings.next();
                varValue = querySolution.get(varName);
                resultList.add(varValue.toString());
                System.out.println(varName + ": " + varValue);
            }
        }

        query.close();
        return resultList;
    }
	
	public List<Record> simpleSearch(String content) throws  Exception{
    	Database database = this.connectUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
        DatabaseManager.registerDatabase(database);
    	String collectionId = "/db/health_care_system/records";
    	Collection col = ConnectUtil.getOrCreateCollection( collectionId, 0, AuthenticationUtilities.loadProperties());
        XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
        xpathService.setProperty("indent", "yes");
        xpathService.setNamespace("", TARGET_NAMESPACE);
        String xpathExp = "/record//*[contains(text(),\"" + content + "\")]/..";
		ResourceSet result = xpathService.query(xpathExp);
        ResourceIterator i = result.getIterator();
        Resource next = null;
        List<Record> records = new ArrayList<Record>();
        Record record = null;
        while(i.hasMoreResources()) {
            try {
                next = i.nextResource();
                JAXBContext context = JAXBContext.newInstance("com.example.xml.model");
                Unmarshaller unmarshaller = context.createUnmarshaller();
                record = (Record) unmarshaller.unmarshal(((XMLResource)next).getContentAsDOM());
                records.add(record);
            } finally {
                try {
                    ((EXistResource)next).freeResources();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
            
        }
        return records;
    }

}
