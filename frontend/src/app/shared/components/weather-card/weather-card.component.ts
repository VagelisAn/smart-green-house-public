import { Component, OnInit } from '@angular/core';
import { Weather } from '../../../core/models/weather';
import { WeatherService } from '../../../core/services/weather.service';
import { CardModule } from 'primeng/card';
import { CommonModule } from '@angular/common';



@Component({
  selector: 'app-weather-card',
  imports: [
    CommonModule,
    CardModule
  ],
  templateUrl: './weather-card.component.html'
})
export class WeatherCardComponent implements OnInit {

  weather?: Weather;

  constructor(
    private weatherService: WeatherService
  ){}

  ngOnInit(): void {
    this.loadWeather();
  }

  loadWeather(){
    this.weatherService.getWeather()
      .subscribe({
        next:(data)=>{
          this.weather=data;
        },
        error:(err)=>{
          console.error('Weather error',err);
        }
      });
  }
}