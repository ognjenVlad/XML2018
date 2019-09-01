import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { PatientsService } from 'src/app/services/patients.service';
import { AuthService } from 'src/app/services/auth.service';
import { Patient } from 'src/app/models/patient.model';
import { Record } from 'src/app/models/record.model';
import { availableAppointments } from 'src/app/constants';
import * as moment from 'moment';

@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.component.html',
  styleUrls: ['./appointments.component.css']
})
export class AppointmentsComponent implements OnInit {
  form = this.fb.group(
    {
      appointment: [
        '',
        [Validators.required],
      ],
      date: ["", Validators.required]
    }
  );
  dateFormat = 'DD/MM/YYYY';
  appointments: any;
  availableAppointments = {};
  freeAppointments = [];
  user: any;
  record: Record;
  constructor(private fb: FormBuilder, private patientsService: PatientsService, private authService: AuthService) { }

  ngOnInit() {
    this.patientsService.getPatient().subscribe((user: Patient) => {
      this.user = user;
      if(user) {
        this.patientsService.getPatientsRecord(user).subscribe((record: Record)=>{
          this.record = record;
          this.patientsService.getDoctorsAppointments(record.doctorId).subscribe(items=>{
            this.appointments = items;
            Object.keys(items).forEach((key)=>{
              this.availableAppointments[key] = this.patientsService.getAvailableAppointments(items[key]);
            })
          })
        })
      }}
    );
  }

  dateChanged() {
    const date = moment(this.form.controls.date.value).format(this.dateFormat);
    if (this.availableAppointments[date]) {
      this.freeAppointments = this.availableAppointments[date];
    } else {
      this.freeAppointments = availableAppointments;
    }
  }

  createAppointment() {
    const date = moment(this.form.controls.date.value).format(this.dateFormat);
    console.log(this.form.controls.appointment.value);
    this.patientsService.saveAppointment(
      {
        patientLbo: this.user.lbo, 
        doctorId: this.record.doctorId, 
        id: "", 
        date: date, 
        time: this.form.controls.appointment.value, 
        isConfirmed: "false"
      })
  }

}
