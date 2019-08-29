import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { JwtService } from './jwt.service';

@Injectable({
  providedIn: 'root'
})
export class PatientsService {
  constructor(private http: HttpClient, private jwtService: JwtService) { }

  getAllDoctors() {
    return this.http.get(`http://localhost:8080/doctor/all-doctors`);
  }

  getPatient() {
    const username = this.jwtService.getUsernameFromToken();
    return this.http.get(`http://localhost:8080/patient/find-by-username/` + `?username=${username}`);
  }
}