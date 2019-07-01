package com.example.xml.repository;

import com.example.xml.dtos.LR1DTO;
import com.example.xml.util.AuthenticationUtilities;
import com.example.xml.util.ConnectUtil;
import com.example.xml.util.DBData;
import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@Repository
public class DocumentRepository {

    @Autowired
    private ConnectUtil connectUtil;

    private static AuthenticationUtilities.ConnectionProperties conn;

    public LR1DTO findById(String id) throws  Exception{
        String collectionId = "/db/health_care_system/documents/lr1";
        String documentId = id.trim();
        DBData data = this.connectUtil.getReourceById( collectionId, documentId, AuthenticationUtilities.loadProperties());
        JAXBContext context = JAXBContext.newInstance("com.example.xml.documents");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        LR1DTO lr1 = (LR1DTO) unmarshaller.unmarshal(data.getResource().getContentAsDOM());
        return lr1;
    }

    public XMLResource save(LR1DTO lr1dto, String id) throws  Exception{
        Database database = this.connectUtil.connectToDatabase(AuthenticationUtilities.loadProperties());
        DatabaseManager.registerDatabase(database);
        String collectionId = "/db/health_care_system/documents/lr1";
        String documentId = id.trim();
        Collection col = null;
        XMLResource res = null;
        OutputStream os = new ByteArrayOutputStream();
        try {
            col = ConnectUtil.getOrCreateCollection(collectionId, 0, AuthenticationUtilities.loadProperties());
            res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);
            JAXBContext context = JAXBContext.newInstance("com.example.xml.documents");
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            String path = new ClassPathResource("schema/LR1.xsd").getFile().getPath();
            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, path);
            marshaller.marshal(lr1dto, os);
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
