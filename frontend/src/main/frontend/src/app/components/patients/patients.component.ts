import { Component, OnInit } from '@angular/core';
import { PatientsService } from 'src/app/services/patients.service';
import { Patient } from 'src/app/models/patient.model';
import { MatDialog, MatDialogConfig } from '@angular/material';
import { ChangePatientModalComponent } from '../change-patient-modal/change-patient-modal.component';
import { Router } from '@angular/router';
import { RecordsService } from 'src/app/services/records.service';

@Component({
  selector: 'app-patients',
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.css']
})
export class PatientsComponent implements OnInit {
  patients: Array<Patient>;
  constructor(private patientsService: PatientsService, private recordsService: RecordsService, private dialog: MatDialog) { 
    this.patientsService.getAllPatients().subscribe((data: any) => {
      data = data.map((item) => {
        return {...item, name: item.name.value}
      });
      this.patients = data;
    })
  }

  ngOnInit() {
  }

  changePatient(item) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.data = {...item};
    let dialogRef = this.dialog.open(ChangePatientModalComponent, dialogConfig);
    dialogRef.componentInstance.onChange.subscribe((item) => {
      console.log(item);
      const index = this.patients.findIndex((appointment) => appointment.id === item.id);
      this.patients[index] = item;
    });
  }

  openLink(patient) {
    window.open(`http://localhost:8181/exist/rest/db/health_care_system/records/${patient.jmbg}?_xsl=../record.xsl`,'_blank');
  }

  download(item) {
    this.recordsService.download(item.jmbg);
  }
}
