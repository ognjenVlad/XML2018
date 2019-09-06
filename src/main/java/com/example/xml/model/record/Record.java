//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.09.06 at 09:56:46 PM CEST 
//


package com.example.xml.model.record;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="patient_jmbg" type="{http://www.health_care.com/record}jmbg"/>
 *         &lt;element name="doctor_id" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="report_ids" type="{http://www.w3.org/2001/XMLSchema}anyType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "record", propOrder = {
    "patientJmbg",
    "doctorId",
    "reportIds"
})
@XmlRootElement(name = "record")
public class Record {

    @XmlElement(name = "patient_jmbg", required = true)
    protected String patientJmbg;
    @XmlElement(name = "doctor_id", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String doctorId;
    @XmlElement(name = "report_ids")
    protected List<String> reportIds;
    @XmlAttribute(name = "id")
    @XmlSchemaType(name = "anyURI")
    protected String id;

    /**
     * Gets the value of the patientJmbg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientJmbg() {
        return patientJmbg;
    }

    /**
     * Sets the value of the patientJmbg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientJmbg(String value) {
        this.patientJmbg = value;
    }

    /**
     * Gets the value of the doctorId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDoctorId() {
        return doctorId;
    }

    /**
     * Sets the value of the doctorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDoctorId(String value) {
        this.doctorId = value;
    }

    /**
     * Gets the value of the reportIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reportIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReportIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<String> getReportIds() {
        if (reportIds == null) {
            reportIds = new ArrayList<String>();
        }
        return this.reportIds;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }
    
    public void setReportIds(List<String> value) {
        this.reportIds = value;
    }

}
