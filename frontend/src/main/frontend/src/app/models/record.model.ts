import { Report } from './report.model';
import { Prescription } from './prescription.model';
import { Referral } from './referral.model';

export interface Record  {
  patientJmbg: string;
  doctorId: string;
  id: string;
  reports?: Array<Report>;
  prescriptions?: Array<Prescription>;
  referrals?: Array<Referral>;
}
