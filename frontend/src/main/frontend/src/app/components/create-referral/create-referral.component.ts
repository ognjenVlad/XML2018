import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { PatientsService } from 'src/app/services/patients.service';
import { Record } from 'src/app/models/record.model';
import { RecordsService } from 'src/app/services/records.service';
import * as moment from 'moment';
import { Referral } from 'src/app/models/referral.model';

@Component({
  selector: 'app-create-referral',
  templateUrl: './create-referral.component.html',
  styleUrls: ['./create-referral.component.css']
})
export class CreateReferralComponent implements OnInit {
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
    const referral: Referral = {
      doctorId: this.record.doctorId,
      patientJmbg: this.record.patientJmbg, 
      id: '',
      date: date,
      time: time,
      toDoctorId: this.form.controls.editor.value
    }
    this.recordService.saveReferral({
      doctorId: this.record.doctorId,
      patientJmbg: this.record.patientJmbg, 
      id: '',
      referrals: [referral]
    })
  }

}
