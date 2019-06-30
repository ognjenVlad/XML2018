import { Injectable } from '@angular/core';
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivate } from '@angular/router';
import { JwtService } from 'src/app/services/jwt.service';


@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private jwtService: JwtService) {  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (this.jwtService.tokenExist() && !this.jwtService.isTokenExpired()) {
      return true;
    }

    this.router.navigate(['login'], { queryParams: { returnUrl: state.url }});
    return false;
  }
}
