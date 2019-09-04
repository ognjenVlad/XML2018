import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router'
import { JwtService } from 'src/app/services/jwt.service';
import { Roles } from 'src/app/models/roles.model';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private jwtService: JwtService, private router: Router) { }

  ngOnInit() {
  }

  logout() {
    this.jwtService.removeToken();
    this.router.navigate(['login']);
  }

  isPatient() {
    return this.jwtService.hasRole(Roles.PATIENT);
  }

  isTechnician() {
    return this.jwtService.hasRole(Roles.TECHNICIAN);
  }
}
