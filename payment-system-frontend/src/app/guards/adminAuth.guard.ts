import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Observable } from 'rxjs';

@Injectable()
export class AdminAuthGuard {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    const requiredRole = route.data['role'];
    const userRole = this.authService.getUserRole();
    alert(`REQUIRED ${requiredRole} USER ROLE ${userRole}`);
    if (this.authService.getIsAuthenticated()) {
      const requiredRole = route.data['role'];
      const userRole = this.authService.getUserRole();
      alert(`REQUIRED ${requiredRole} USER ROLE ${userRole}`);
      if (userRole == requiredRole) return true;
      else return this.router.parseUrl('/admin/dashboard');
    }
    return this.router.parseUrl('/login');
  }
}
