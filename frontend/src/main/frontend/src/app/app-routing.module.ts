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
import { GeneratePdfComponent } from './components/generate-pdf/generate-pdf.component';
import { RecordsComponent } from './components/records/records.component';
import { PatientsComponent } from './components/patients/patients.component';
import { DoctorGuard } from './shared/guards/doctor.guard';
import { ReportsComponent } from './components/reports/reports.component';
import { CreatePrescriptionComponent } from './components/create-prescription/create-prescription.component';
import { CreateReferralComponent } from './components/create-referral/create-referral.component';


const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  { path: 'choose-doctor', component: ChooseDoctorComponent, canActivate: [PatientGuard]},
  { path: 'login', component: LoginComponent, canActivate: [AnonymousGuard]},
  { path: 'register', component: RegisterComponent , canActivate: [AnonymousGuard]},
  { path: 'make-appointment', component: AppointmentsComponent, canActivate: [PatientGuard]},
  { path: 'appointment-change', component: AppointmentChangeComponent, canActivate: [TechnicianGuard]},
  { path: 'documentation', component: GeneratePdfComponent, canActivate: [TechnicianGuard]},
  { path: 'record', component: RecordsComponent, canActivate: [PatientGuard]},
  { path: 'patients', component: PatientsComponent, canActivate: [DoctorGuard]},
  { path: 'report/:id', component: ReportsComponent, canActivate: [DoctorGuard]},
  { path: 'prescription/:id', component: CreatePrescriptionComponent, canActivate: [DoctorGuard]},
  { path: 'referral/:id', component: CreateReferralComponent, canActivate: [DoctorGuard]}
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
