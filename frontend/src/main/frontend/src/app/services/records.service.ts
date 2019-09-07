import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Record } from '../models/record.model';

@Injectable({
  providedIn: 'root'
})
export class RecordsService {

  constructor(private http: HttpClient) { }

  save(record: Record) {
    return this.http.post(`http://localhost:8080/record/create`, record).toPromise().
    then(() => {
      alert("Your personal doctor has been changed!")
    }).catch((error) => {
      
    });
  }

  saveReport(record: Record) {
    return this.http.post(`http://localhost:8080/record/create-report`, record).toPromise().
    then(() => {
      alert("Report created!")
    }).catch((error) => {
      
    }) ;;
  }

  saveReferral(record: Record) {
    return this.http.post(`http://localhost:8080/record/create-referral`, record).toPromise().
    then(() => {
      alert("Referral created!")
    }).catch((error) => {
      
    }) ;;
  }

  savePrescription(record: Record) {
    return this.http.post(`http://localhost:8080/record/create-prescription`, record).toPromise().
    then(() => {
      alert("Prescription created!")
    }).catch((error) => {
      
    }) ;;
  }

  download(id) {
    return this.http.get(`http://localhost:8080/record/download?id=${id}`, {responseType: 'blob'}).subscribe((response) => {
      let blob = new Blob([<Blob>response], { type: 'application/zip'});
      let link = document.createElement('a');
      link.href = window.URL.createObjectURL(blob);
      link.download = id + '_storage' + '.pdf';
      link.dispatchEvent(
        new MouseEvent(`click`, {
          bubbles: true,
          cancelable: true,
          view: window
      }));
    });
  }
}