import { Component, OnInit } from '@angular/core';
import { PatientsService } from 'src/app/services/patients.service';
import { Doctor } from 'src/app/models/doctor.model';
import { MatDialogConfig, MatDialog } from '@angular/material';
import { AppointmentChangeModalComponent } from '../appointment-change-modal/appointment-change-modal.component';

@Component({
  selector: 'app-appointment-change',
  templateUrl: './appointment-change.component.html',
  styleUrls: ['./appointment-change.component.css']
})
export class AppointmentChangeComponent implements OnInit {
  doctors: Array<Doctor>;
  appointments: any;

  constructor(private patientsService: PatientsService, private dialog: MatDialog) { 
    this.patientsService.getAllDoctors().subscribe((data: any) => {
      data = data.map((item) => {
        return {...item, name: item.name.value}
      });
      this.doctors = data;
    })
  }

  ngOnInit() {
  }

  changeDoctor(event) {
    this.patientsService.allAppointmentsForDoctor(event.value.jmbg).subscribe(items=>{
      this.appointments = items;
    })
  }

  changeAppointment(item) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.data = {...item};
    let dialogRef = this.dialog.open(AppointmentChangeModalComponent, dialogConfig);
    dialogRef.componentInstance.onChange.subscribe((item) => {
      const index = this.appointments.findIndex((appointment) => appointment.id === item.id);
      this.appointments[index] = item;
    });
  }

}
