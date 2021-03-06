import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
    name: ['', Validators.required],
    lastname: ['', Validators.required],
    jmbg: ['', [Validators.required, Validators.minLength(13), Validators.maxLength(13)]],
    lbo: ['', [Validators.required, Validators.minLength(11), Validators.maxLength(11)]],
    phone: ['']
  });

  constructor(private router: Router, private fb: FormBuilder, private authService: AuthService) { }

  ngOnInit() {
  }


  register() {
    if (!this.registerForm.valid) {
      return;
    }
    this.authService.register(
      { username: this.registerForm.get('username').value,
        password: this.registerForm.get('password').value,
        name: this.registerForm.get('name').value,
        lastname: this.registerForm.get('lastname').value,
        jmbg: this.registerForm.get('jmbg').value,
        lbo: this.registerForm.get('lbo').value,
        phone: this.registerForm.get('phone').value
    }).subscribe((response) => {
      this.router.navigate(['home']);
    });
  }

}
