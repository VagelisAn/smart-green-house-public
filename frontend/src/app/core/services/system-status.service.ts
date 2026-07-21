import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SystemStatus } from '../models/systemStatus';
import { environment } from '../../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class SystemStatusService {

  private apiUrl = `${environment.apiUrl}/system/status`;

  constructor(
    private http: HttpClient
  ){}

  getStatus(): Observable<SystemStatus>{
    return this.http.get<SystemStatus>(
        this.apiUrl
    );
  }
}