export interface Report  {
  patientJmbg: string;
  doctorId: string;
  time: string;
  date: string;
  id: string;
  opinion: string;
  diagnosisId?: Array<any>;
}
