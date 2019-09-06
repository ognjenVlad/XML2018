import { Component, OnInit, Inject, EventEmitter } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { PatientsService } from 'src/app/services/patients.service';
import * as moment from 'moment';
import { Validators, FormBuilder } from '@angular/forms';
import { availableAppointments } from 'src/app/constants';

@Component({
  selector: 'app-appointment-change-modal',
  templateUrl: './appointment-change-modal.component.html',
  styleUrls: ['./appointment-change-modal.component.css']
})
export class AppointmentChangeModalComponent implements OnInit {
  form = this.fb.group(
    {
      appointment: [
        ''
      ],
      date: [""],
      isConfirmed: [this.data.isConfirmed]
    }
  );
  dateFormat = 'DD/MM/YYYY';
  onChange = new EventEmitter();
  availableAppointments = {};
  freeAppointments: any;
  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<AppointmentChangeModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private patientsService: PatientsService
  ) { }

  ngOnInit() {
    console.log(this.data);
    this.patientsService.getDoctorsAppointments(this.data.doctorId).subscribe(items=>{
      Object.keys(items).forEach((key)=>{
        this.availableAppointments[key] = this.patientsService.getAvailableAppointments(items[key]);
      })
    })
  }

  close() {
    this.dialogRef.close();
  }

  dateChanged() {
    const date = moment(this.form.controls.date.value).format(this.dateFormat);
    if (this.availableAppointments[date]) {
      this.freeAppointments = this.availableAppointments[date];
    } else {
      this.freeAppointments = availableAppointments;
    }
  }

  changeAppointment() {
    let newDate = this.form.controls.date.value;
    if (this.form.controls.date.value && !this.form.controls.appointment.value) {
      return;
    } else if (!this.form.controls.appointment.value && !this.form.controls.date.value) {
      this.form.controls.appointment.setValue(this.data.time);
      newDate = this.data.date;
    }
    const date = moment(newDate).format(this.dateFormat);
    console.log(this.form.controls.appointment.value);
    const newAppointment = {
      patientLbo: this.data.patientLbo, 
      doctorId: this.data.doctorId, 
      id: this.data.id, 
      date: date, 
      time: this.form.controls.appointment.value, 
      isConfirmed: this.form.controls.isConfirmed.value
    };
    this.patientsService.changeAppointment(newAppointment);
    this.onChange.emit(newAppointment);
    this.dialogRef.close();
  }

}
