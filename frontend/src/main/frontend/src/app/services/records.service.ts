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
      
    }) ;;
  }
}