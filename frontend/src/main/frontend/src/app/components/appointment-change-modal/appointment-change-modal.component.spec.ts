import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmentChangeModalComponent } from './appointment-change-modal.component';

describe('AppointmentChangeModalComponent', () => {
  let component: AppointmentChangeModalComponent;
  let fixture: ComponentFixture<AppointmentChangeModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppointmentChangeModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppointmentChangeModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
