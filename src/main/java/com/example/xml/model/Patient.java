//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.07.01 at 08:55:27 PM CEST 
//


package com.example.xml.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for patient complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="patient">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.health_care.com/user}user">
 *       &lt;sequence>
 *         &lt;element name="phone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lbo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "patient", propOrder = {
    "phone",
    "lbo"
})
@XmlRootElement(name = "patient")
public class Patient
    extends User
{

    @XmlElement(required = true)
    protected String phone;
    @XmlElement(required = true)
    protected String lbo;

    /**
     * Gets the value of the phone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * Gets the value of the lbo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLbo() {
        return lbo;
    }

    /**
     * Sets the value of the lbo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLbo(String value) {
        this.lbo = value;
    }

}
