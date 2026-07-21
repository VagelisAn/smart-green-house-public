import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Weather } from '../models/weather';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WeatherService {
  
  private baseUrl = `${environment.apiUrl}/weather`
  constructor(
    private http: HttpClient
  ){}
  getWeather(): Observable<Weather>{
    return this.http.get<Weather>(this.baseUrl);
  }

}