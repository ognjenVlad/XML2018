import { Component, OnInit } from '@angular/core';
import { PatientsService } from 'src/app/services/patients.service';
import { Patient } from 'src/app/models/patient.model';
import { Record } from 'src/app/models/record.model';

@Component({
  selector: 'app-records',
  templateUrl: './records.component.html',
  styleUrls: ['./records.component.css']
})
export class RecordsComponent implements OnInit {
  user: Patient;
  record: Record;
  constructor(private patientsService: PatientsService) { }

  ngOnInit() {
    this.patientsService.getPatient().subscribe((user: Patient) => {
      this.user = user;
      if(user) {
        this.patientsService.getPatientsRecord(user).subscribe((record: Record)=>{
          this.record = record;
        })
      }
    });
  }

  openLink() {
    window.open(`http://localhost:8181/exist/rest/db/health_care_system/records/${this.record.patientJmbg}?_xsl=../record.xsl`,'_blank');
  }

}
