import { Injectable } from '@angular/core';
import { JwtService } from './jwt.service';
import { HttpClient } from '@angular/common/http';
import { throwError } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private jwtService: JwtService, private router: Router) { }

  login(credentials: any) {
    this.http.post(`http://localhost:8080/auth/login`, credentials, {responseType: 'text'}).toPromise().then
      ((token: any) => {
        this.jwtService.setToken(token);
        this.router.navigate(['home']);
    }).catch((error) => {
      this.router.navigate(['login']);
    }) ;
  }

  register(credentials: any) {
    return this.http.post(`http://localhost:8080/auth/register`, credentials);
  }

  private handleErrors(response: Response) {
    return throwError(new Error);
  }
}
