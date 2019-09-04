import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { JwtService } from './services/jwt.service';
import { RegisterComponent } from './components/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { AnonymousGuard } from './shared/guards/anonymous.guard';
import { AuthGuard } from './shared/guards/auth.guard';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { ChooseDoctorComponent } from './components/choose-doctor/choose-doctor.component';
import { AppointmentsComponent } from './components/appointments/appointments.component';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_DATE_LOCALE, DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule, MatDialogModule } from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { SearchComponent } from './components/search/search.component';
import { AppointmentChangeComponent } from './components/appointment-change/appointment-change.component';
import { TechnicianGuard } from './shared/guards/technician.guard';
import { PatientGuard } from './shared/guards/patient.guard';
import { AppointmentChangeModalComponent } from './components/appointment-change-modal/appointment-change-modal.component';
import {MatCheckboxModule} from '@angular/material/checkbox';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    NavbarComponent,
    ChooseDoctorComponent,
    AppointmentsComponent,
    SearchComponent,
    AppointmentChangeComponent,
    AppointmentChangeModalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule,
    BrowserAnimationsModule,
    MatDialogModule,
    MatCheckboxModule
  ],
  exports: [MatDatepickerModule],
  providers: [JwtService, AnonymousGuard, AuthGuard, TechnicianGuard, PatientGuard],
  entryComponents: [AppointmentChangeModalComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
