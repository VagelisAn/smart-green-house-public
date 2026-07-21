import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';

import { SensorService } from '../../../core/services/sensor.service';
import { Sensor } from '../../../core/models/sensor';
import { ConfirmationService } from 'primeng/api';


@Component({
  selector: 'app-sensor-list',
  standalone: true,
  imports: [
    CommonModule,
    TableModule,
    ButtonModule,
    CardModule,
    TagModule
  ],
  templateUrl: './sensor-list.component.html'
})
export class SensorListComponent implements OnInit {
  sensors: Sensor[] = [];

  @Output()
  sensorSelected =
    new EventEmitter<Sensor>();

  @Output()
  edit = new EventEmitter<Sensor>();

  @Output()
  deleted = new EventEmitter<void>();

  constructor(
    private sensorService: SensorService
  ) { }
  ngOnInit() {
    this.loadSensors();
  }
  loadSensors() {
    this.sensorService
      .getAllWithStatus()
      .subscribe(data => {
        this.sensors = data;
        console.log(data)
      });
  }
  editSensor(sensor: Sensor) {
    this.edit.emit(sensor);
  }
  deleteSensor(sensor: Sensor) {
    if (confirm(`Delete ${sensor.name}?`)) {
      this.sensorService
        .delete(sensor.id!)
        .subscribe(() => {
          this.deleted.emit();
        });
    }
  }
  selectSensor(sensor: Sensor) {
    this.sensorSelected.emit(sensor);
  }
}