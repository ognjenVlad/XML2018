import { Component, OnInit } from '@angular/core';
import { PatientsService } from 'src/app/services/patients.service';
import { RecordsService } from 'src/app/services/records.service';

@Component({
  selector: 'app-generate-pdf',
  templateUrl: './generate-pdf.component.html',
  styleUrls: ['./generate-pdf.component.css']
})
export class GeneratePdfComponent implements OnInit {
  patients: any;
  constructor(private patientsService: PatientsService, private recordsService: RecordsService) { }

  ngOnInit() {
    this.patientsService.getAllPatients().subscribe((data: any) => {
      data = data.map((item) => {
        return {...item, name: item.name.value}
      });
      this.patients = data;
    })
  }

  download(item) {
    this.recordsService.download(item.jmbg);
  }

  downloadAnon(item) {
    this.recordsService.downloadAnon(item.jmbg);
  }

  openLink(patient) {
    window.open(`http://localhost:8181/exist/rest/db/health_care_system/records/${patient.jmbg}?_xsl=../record.xsl`,'_blank');
  }

}
