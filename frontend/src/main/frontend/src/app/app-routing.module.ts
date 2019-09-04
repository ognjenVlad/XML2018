import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AuthGuard } from './shared/guards/auth.guard';
import { HomeComponent } from './components/home/home.component';
import { AnonymousGuard } from './shared/guards/anonymous.guard';
import { ChooseDoctorComponent } from './components/choose-doctor/choose-doctor.component';
import { AppointmentsComponent } from './components/appointments/appointments.component';
import { TechnicianGuard } from './shared/guards/technician.guard';
import { AppointmentChangeComponent } from './components/appointment-change/appointment-change.component';
import { PatientGuard } from './shared/guards/patient.guard';


const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  { path: 'choose-doctor', component: ChooseDoctorComponent, canActivate: [PatientGuard]},
  { path: 'login', component: LoginComponent, canActivate: [AnonymousGuard]},
  { path: 'register', component: RegisterComponent , canActivate: [AnonymousGuard]},
  { path: 'make-appointment', component: AppointmentsComponent, canActivate: [PatientGuard]},
  { path: 'appointment-change', component: AppointmentChangeComponent, canActivate: [TechnicianGuard]},
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
