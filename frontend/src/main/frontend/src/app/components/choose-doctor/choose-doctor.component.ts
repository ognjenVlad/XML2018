import { Component, OnInit } from '@angular/core';
import { PatientsService } from 'src/app/services/patients.service';
import { Doctor } from 'src/app/models/doctor.model';
import { Patient } from 'src/app/models/patient.model';
import { RecordsService } from 'src/app/services/records.service';

@Component({
  selector: 'app-choose-doctor',
  templateUrl: './choose-doctor.component.html',
  styleUrls: ['./choose-doctor.component.css']
})
export class ChooseDoctorComponent implements OnInit {

  doctors: Array<Doctor>;
  patient: Patient;
  constructor(private patientsService: PatientsService, private recordService: RecordsService) { }

  ngOnInit() {
    this.patientsService.getAllDoctors().subscribe((data: any) => {
      data = data.map((item) => {
        return {...item, name: item.name.value}
      });
      this.doctors = data;
    })

    this.patientsService.getPatient().subscribe((data: any) => {
      this.patient = {...data, name: data.name.value};
    })
  }

  selectDoctor(doc: Doctor) {
    this.recordService.save({doctorId: doc.jmbg, patientLbo: this.patient.lbo, id: ''})
  }

}
