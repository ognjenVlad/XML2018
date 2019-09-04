import { Injectable } from '@angular/core';
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivate } from '@angular/router';
import { JwtService } from 'src/app/services/jwt.service';
import { Roles } from 'src/app/models/roles.model';


@Injectable()
export class TechnicianGuard implements CanActivate {

  constructor(private router: Router, private jwtService: JwtService) {  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (this.jwtService.tokenExist() && !this.jwtService.isTokenExpired() && this.jwtService.hasRole(Roles.TECHNICIAN)) {
      return true;
    }

    this.router.navigate(['login'], { queryParams: { returnUrl: state.url }});
    return false;
  }
}
