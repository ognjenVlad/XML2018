//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.09.04 at 06:59:47 PM CEST 
//


package com.example.xml.model;

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
 *         &lt;element name="patient_lbo" type="{http://www.health_care.com/record}lbo"/>
 *         &lt;element name="doctor_id" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
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
@XmlType(name = "", propOrder = {
    "patientLbo",
    "doctorId"
})
@XmlRootElement(name = "record")
public class Record {

    @XmlElement(name = "patient_lbo", required = true)
    protected String patientLbo;
    @XmlElement(name = "doctor_id", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String doctorId;
    @XmlAttribute(name = "id")
    @XmlSchemaType(name = "anyURI")
    protected String id;

    /**
     * Gets the value of the patientLbo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientLbo() {
        return patientLbo;
    }

    /**
     * Sets the value of the patientLbo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientLbo(String value) {
        this.patientLbo = value;
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

}