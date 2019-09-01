import { Injectable } from '@angular/core';
import { JwtService } from './jwt.service';
import { HttpClient } from '@angular/common/http';
import { throwError } from 'rxjs';
import { Router } from '@angular/router';
import { Patient } from '../models/patient.model';
import { PatientsService } from './patients.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  user = null;
  constructor(private http: HttpClient, private jwtService: JwtService, private router: Router, private patientService: PatientsService) { }

  login(credentials: any) {
    this.http.post(`http://localhost:8080/auth/login`, credentials, {responseType: 'text'}).toPromise().then
      ((token: any) => {
        this.jwtService.setToken(token);
        this.getUser()
        this.router.navigate(['home']);
    }).catch((error) => {
      this.router.navigate(['login']);
    }) ;
  }

  register(credentials: any) {
    return this.http.post(`http://localhost:8080/auth/register`, credentials);
  }

  getUser() {
    if (this.user) {
      return;
    }
    this.patientService.getPatient().subscribe((user: Patient) => {
      if(user) {
        this.user = user;
      }
    });
  }

  private handleErrors(response: Response) {
    return throwError(new Error);
  }
}
