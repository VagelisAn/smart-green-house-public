import { Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Sensor } from '../../../core/models/sensor';
import { SensorListComponent } from '../sensor-list/sensor-list.component';
import { SensorDetailComponent } from '../sensor-detail/sensor-detail.component';
import { AddSensorDialogComponent } from '../add-sensor-dialog/add-sensor-dialog.component';

import { ButtonModule } from 'primeng/button';
import { WeatherCardComponent } from '../../../shared/components/weather-card/weather-card.component';
import { SystemStatusCardComponent } from '../../../shared/components/system-status-card/system-status-card.component';

@Component({
  selector: 'app-sensor-page',
  standalone: true,
  imports: [
    CommonModule,
    SensorListComponent,
    SensorDetailComponent,
    AddSensorDialogComponent,
    ButtonModule,
    WeatherCardComponent,
    SystemStatusCardComponent
  ],
  templateUrl: './sensor-page.component.html',
  styleUrl: './sensor-page.component.css'
})
export class SensorPageComponent {

  currentView:'list'|'details'='list';
  selectedSensor!:Sensor;

  @ViewChild(SensorListComponent)
  sensorList!:SensorListComponent;

  @ViewChild('addDialog')
  addDialog!:AddSensorDialogComponent;

  selectSensor(sensor:Sensor){
    this.selectedSensor=sensor;
    this.currentView='details';
  }

  goBack(){
    this.currentView='list';
    this.selectedSensor=null!;
  }

  openAddSensor(){
    this.addDialog.open();
  }

  reloadSensors(){
    this.sensorList.loadSensors();
  }

  openEdit(sensor:Sensor){
    console.log('Edit sensor',sensor);
  }
}