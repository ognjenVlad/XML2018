import { Component, OnInit, Inject, EventEmitter } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { PatientsService } from 'src/app/services/patients.service';
import { Validators, FormBuilder } from '@angular/forms';
import { availableAppointments } from 'src/app/constants';
import { Record } from 'src/app/models/record.model';
import { RecordsService } from 'src/app/services/records.service';

@Component({
  selector: 'app-change-patient-modal',
  templateUrl: './change-patient-modal.component.html',
  styleUrls: ['./change-patient-modal.component.css']
})
export class ChangePatientModalComponent implements OnInit {
  
  dateFormat = 'DD/MM/YYYY';
  onChange = new EventEmitter();
  availableAppointments = {};
  freeAppointments: any;
  record: Record;
  form = this.fb.group(
    {
      patientJmbg: ['', [Validators.required]],
      doctorId: ['', [Validators.required]],
    }
  );
  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ChangePatientModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private patientsService: PatientsService,
    private recordsService: RecordsService
  ) { }

  ngOnInit() {
    this.patientsService.getPatientsRecord(this.data).subscribe((data: Record)=>{
      if (data) {
        this.form.controls.doctorId.setValue(data.doctorId);
        this.form.controls.patientJmbg.setValue(data.patientJmbg);
        this.record = data;
      }

      console.log(this.record);
    })
  }

  close() {
    this.dialogRef.close();
  }
  changeAppointment() {
    if (this.form.invalid) {
      return;
    } 

    const jmbg = this.form.controls.patientJmbg.value;
    const docId = this.form.controls.doctorId.value;
    this.recordsService.save({doctorId: docId, patientJmbg: this.data.jmbg, id: ''})
    this.onChange.emit(this.data);
    this.dialogRef.close();
  }

}
