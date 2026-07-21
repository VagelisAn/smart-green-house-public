import { HttpClient } from "@angular/common/http";
import { Sensor } from "../models/sensor";
import { Injectable } from "@angular/core";
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SensorService {

  private baseUrl = `${environment.apiUrl}/sensors`;

  constructor(private http: HttpClient) { }

  getAll() {
    return this.http.get<Sensor[]>(this.baseUrl);
  }

  getAllWithStatus() {
    return this.http.get<Sensor[]>(`${this.baseUrl}/status`);
  }

  save(sensor: Sensor) {
    return this.http.post(this.baseUrl, sensor);
  }
  delete(id: number) {
    return this.http.delete(
      `${this.baseUrl}/${id}`
    );
  }
  update(sensor: Sensor) {
    return this.http.put(
      `${this.baseUrl}/${sensor.id}`,
      sensor
    );
  }
}