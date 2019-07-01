package com.example.xml.dtos;

import java.util.Date;

public class LR1DTO {

    private String institutionName;
    private String insuredName;
    private Date dateOfBirth;
    private String releaseOfParticipationBasis;
    private String insuranceCardNumber;
    private String country;
    private Date medicinePrescriptionDate;
    private String medicalCardNumber;
    private String doctorIdNumber;
    private String assignDoctorCode;
    private String diagnosis;
    private String comment;
    private Date medicineDateOfIssue;
    private String doctorOfIssueCode;
    private String serialNumber;
    private String amount;
    private String medicineReceiver;

    public LR1DTO() {
    }

    public LR1DTO(String institutionName, String insuredName, Date dateOfBirth, String releaseOfParticipationBasis,
                  String insuranceCardNumber, String country, Date medicinePrescriptionDate, String medicalCardNumber,
                  String doctorIdNumber, String assignDoctorCode, String diagnosis, String comment,
                  Date medicineDateOfIssue, String doctorOfIssueCode,
                  String serialNumber, String amount,String medicineReceiver) {
        this.institutionName = institutionName;
        this.insuredName = insuredName;
        this.dateOfBirth = dateOfBirth;
        this.releaseOfParticipationBasis = releaseOfParticipationBasis;
        this.insuranceCardNumber = insuranceCardNumber;
        this.country = country;
        this.medicinePrescriptionDate = medicinePrescriptionDate;
        this.medicalCardNumber = medicalCardNumber;
        this.doctorIdNumber = doctorIdNumber;
        this.assignDoctorCode = assignDoctorCode;
        this.diagnosis = diagnosis;
        this.comment = comment;
        this.medicineDateOfIssue = medicineDateOfIssue;
        this.doctorOfIssueCode = doctorOfIssueCode;
        this.serialNumber = serialNumber;
        this.amount = amount;
        this.medicineReceiver = medicineReceiver;
    }


    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getInsuredName() {
        return insuredName;
    }

    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getReleaseOfParticipationBasis() {
        return releaseOfParticipationBasis;
    }

    public void setReleaseOfParticipationBasis(String releaseOfParticipationBasis) {
        this.releaseOfParticipationBasis = releaseOfParticipationBasis;
    }

    public String getInsuranceCardNumber() {
        return insuranceCardNumber;
    }

    public void setInsuranceCardNumber(String insuranceCardNumber) {
        this.insuranceCardNumber = insuranceCardNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getMedicinePrescriptionDate() {
        return medicinePrescriptionDate;
    }

    public void setMedicinePrescriptionDate(Date medicinePrescriptionDate) {
        this.medicinePrescriptionDate = medicinePrescriptionDate;
    }

    public String getMedicalCardNumber() {
        return medicalCardNumber;
    }

    public void setMedicalCardNumber(String medicalCardNumber) {
        this.medicalCardNumber = medicalCardNumber;
    }

    public String getDoctorIdNumber() {
        return doctorIdNumber;
    }

    public void setDoctorIdNumber(String doctorIdNumber) {
        this.doctorIdNumber = doctorIdNumber;
    }

    public String getAssignDoctorCode() {
        return assignDoctorCode;
    }

    public void setAssignDoctorCode(String assignDoctorCode) {
        this.assignDoctorCode = assignDoctorCode;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getMedicineDateOfIssue() {
        return medicineDateOfIssue;
    }

    public void setMedicineDateOfIssue(Date medicineDateOfIssue) {
        this.medicineDateOfIssue = medicineDateOfIssue;
    }

    public String getDoctorOfIssueCode() {
        return doctorOfIssueCode;
    }

    public void setDoctorOfIssueCode(String doctorOfIssueCode) {
        this.doctorOfIssueCode = doctorOfIssueCode;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMedicineReceiver() {
        return medicineReceiver;
    }

    public void setMedicineReceiver(String medicineReceiver) {
        this.medicineReceiver = medicineReceiver;
    }
}
