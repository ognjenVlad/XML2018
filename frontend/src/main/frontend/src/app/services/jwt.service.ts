import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Token } from '../models/token.model';

@Injectable()
export class JwtService {
  private jwtHelper: JwtHelperService;

  constructor() {
    this.jwtHelper = new JwtHelperService();
  }

  tokenExist(): boolean {
    return !!(localStorage.getItem('Authentication-Token'));
  }

  isTokenExpired(): boolean {
    const refreshToken = this.getToken();
    return this.jwtHelper.isTokenExpired(refreshToken);
  }

  hasRole(role: string) {
    return this.getRolesFromToken().find((item:any)=> item.authority === role);
  }

  getRolesFromToken(): Array<string> {
    const tokenDecoded: Token = this.decodeToken();
    return tokenDecoded ? tokenDecoded.roles : Array<string>();
  }

  getUsernameFromToken(): string {
    const tokenDecoded: Token = this.decodeToken();
    return tokenDecoded.sub;
  }

  getIdFromToken(): number {
    const tokenDecoded: Token = this.decodeToken();
    return tokenDecoded.id;
  }

  getToken(): string {
    return localStorage.getItem('Authentication-Token');
  }

  setToken(token: string): void {
    localStorage.setItem('Authentication-Token', token);
  }

  removeToken(): void {
    localStorage.removeItem('Authentication-Token');
  }

  private decodeToken(): Token {
    return this.jwtHelper.decodeToken(this.getToken());
  }
}
