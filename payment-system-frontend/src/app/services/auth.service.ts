import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private username: string = '';
  private isAuthenticated: boolean = false;
  private role: 'user' | 'admin' | undefined = undefined;

  constructor(private router: Router) {}

  getUsername(): string {
    return this.username;
  }

  getIsAuthenticated(): boolean {
    return this.isAuthenticated;
  }

  getUserRole(): string | undefined {
    return this.role;
  }

  login(username: string): boolean {
    this.username = username;
    this.isAuthenticated = true;
    this.role = 'user';
    return true;
  }

  logout(): void {
    this.username = '';
    this.isAuthenticated = false;
    this.router.navigateByUrl('/login');
  }
}
