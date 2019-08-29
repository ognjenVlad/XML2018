export interface Doctor {
  username: string;
  name: string;
  lastname: string;
  jmbg: string;
  id: string;
  specialty: string;
  patientId: Array<string>;
}
