import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Measurement } from "../models/measurement";
import { environment } from "../../../environments/environment";


@Injectable({
    providedIn:'root'
  })
  export class MeasurementService {
  
    private baseUrl=`${environment.apiUrl}`;
  
    constructor(private http:HttpClient){}
  
    getMeasurements(sensorId:number){
      return this.http.get<Measurement[]>(
        `${this.baseUrl}/measurements/sensor/${sensorId}`
      );
    }
  
    getRealtime(sensorId:number){
      return this.http.get<Measurement[]>(
        `${this.baseUrl}/realtime/${sensorId}`
      );
    }
  }