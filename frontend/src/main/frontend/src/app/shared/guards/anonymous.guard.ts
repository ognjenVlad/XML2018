import { Injectable } from '@angular/core';
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivate } from '@angular/router';
import { JwtService } from 'src/app/services/jwt.service';


@Injectable()
export class AnonymousGuard implements CanActivate {

  constructor(private router: Router, private jwtService: JwtService) {  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    console.log(this.jwtService.tokenExist());
    console.log(this.jwtService.isTokenExpired());
    if (this.jwtService.tokenExist() && !this.jwtService.isTokenExpired()) {
      this.router.navigate(['home'], { queryParams: { returnUrl: state.url }});
      return false;
    }
    return true;
  }
}
