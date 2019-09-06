import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { PatientsService } from 'src/app/services/patients.service';
import { Record } from 'src/app/models/record.model';
import { RecordsService } from 'src/app/services/records.service';
import * as moment from 'moment';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {
  form = this.fb.group(
    {
      editor: []
    }
  );
  dateFormat = 'DD/MM/YYYY';
  timeFormat = 'HH:mm';
  record: Record;
  constructor(private recordService: RecordsService, private patientsService: PatientsService, private fb: FormBuilder, private actitedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.actitedRoute.params.subscribe(item=>{
      console.log(item.id);
      const user = {jmbg: item.id};
      this.patientsService.getPatientsRecord(user).subscribe((record: Record)=>{
        this.record = record;
      })
    })
  }

  submit() {
    const date = moment(new Date()).format(this.dateFormat);
    const time = moment(new Date()).format(this.timeFormat);
    const report = {
      doctorId: this.record.doctorId,
      patientJmbg: this.record.patientJmbg, 
      id: '',
      date: date,
      time: time,
      opinion: this.form.controls.editor.value
    }
    this.recordService.saveReport({
      doctorId: this.record.doctorId,
      patientJmbg: this.record.patientJmbg, 
      id: '',
      reports: [report]
    })
  }

}
