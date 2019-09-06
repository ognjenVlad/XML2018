import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangePatientModalComponent } from './change-patient-modal.component';

describe('ChangePatientModalComponent', () => {
  let component: ChangePatientModalComponent;
  let fixture: ComponentFixture<ChangePatientModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangePatientModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangePatientModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
