import { Component, signal } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { merge } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  form: FormGroup;

  errorEmailMessage = signal('');
  errorPasswordMessage = signal('');

  hide = signal(true);

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
    });

    const emailCtrl = this.form.get('email')!;
    const passwordCtrl = this.form.get('password')!;

    merge(
      emailCtrl.statusChanges,
      emailCtrl.valueChanges,
      passwordCtrl.statusChanges,
      passwordCtrl.valueChanges
    )
      .pipe(takeUntilDestroyed())
      .subscribe(() => this.updateErrorMessage());
  }

  updateErrorMessage(controlName?: 'email' | 'password') {
    const emailCtrl = this.form.get('email')!;
    const passwordCtrl = this.form.get('password')!;

    const setMsg = (ctrl: any, set: (v: string) => void) => {
      if (ctrl.hasError('required')) {
        set('You must enter a value');
      } else if (ctrl.hasError('email')) {
        set('Not a valid email');
      } else if (ctrl.hasError('minlength')) {
        const min = ctrl.getError('minlength')?.requiredLength ?? 8;
        set(`Password must be at least ${min} characters`);
      } else {
        set('');
      }
    };

    if (!controlName || controlName === 'email') {
      setMsg(emailCtrl, this.errorEmailMessage.set);
    }
    if (!controlName || controlName === 'password') {
      setMsg(passwordCtrl, this.errorPasswordMessage.set);
    }
  }

  clickEvent(event: MouseEvent) {
    this.hide.set(!this.hide());
    event.stopPropagation();
  }

  login() {
    if (this.form.invalid) return;

    const { email, password } = this.form.getRawValue();
    // alert(`Email: ${email}, Password: ${password}`);
    const resp = this.authService.login(email);

    if (resp) this.router.navigateByUrl('/admin');
  }
}
