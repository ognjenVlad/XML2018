import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JwtService } from './jwt.service';
import { map } from 'rxjs/operators';
import { Appointment } from '../models/appointment.model';
import { availableAppointments } from '../constants';
import { Patient } from '../models/patient.model';

@Injectable({
  providedIn: 'root'
})
export class PatientsService {
  record;
  constructor(private http: HttpClient, private jwtService: JwtService) { }

  getAllDoctors() {
    return this.http.get(`http://localhost:8080/doctor/all-doctors`);
  }

  getPatient() {
    const username = this.jwtService.getUsernameFromToken();
    if(username) {
      return this.http.get(`http://localhost:8080/patient/find-by-username` + `?username=${username}`);
    }
  }

  getPatientsRecord(user) {
    return this.http.get(`http://localhost:8080/record` + `?id=${user.lbo}`);
  }

  getDoctorsAppointments(doctorId: string) {
    return this.http.get(`http://localhost:8080/appointments/doctor` + `?id=${doctorId}`).pipe(map((items: Array<Appointment>)=>{
      let usedAppointments = {}; 
      items.forEach(element => {
        if(usedAppointments[element.date.toString()]) {
          usedAppointments[element.date.toString()].push(element);
        } else {
          usedAppointments[element.date.toString()] = [];
          usedAppointments[element.date.toString()].push(element);
        }
      });
      return usedAppointments;
    }));
  }

  getAvailableAppointments(items) {
    let appointments = [...availableAppointments];
    items.forEach((item) => {
      const found = appointments.findIndex((value) => value === item.time)
      if (found) {
        appointments.splice(found, 1);
      }
    })
    return appointments;
  }

  saveAppointment(appointment: Appointment) {
    return this.http.post(`http://localhost:8080/appointments/create`, appointment).toPromise().then
    (() => {
      alert("Appointment created!")
  }).catch((error) => {
  }) ;;
  }
}