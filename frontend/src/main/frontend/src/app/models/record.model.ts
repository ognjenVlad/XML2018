import { Report } from './report.model';

export interface Record  {
  patientJmbg: string;
  doctorId: string;
  id: string;
  reports?: Array<Report>;
}
