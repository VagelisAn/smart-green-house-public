import { Component, Input, OnChanges, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { Sensor } from '../../../core/models/sensor';
import { Measurement } from '../../../core/models/measurement';
import { MeasurementService } from '../../../core/services/measurement.service';
import { SensorChartComponent } from '../../../shared/components/chart/sensor-chart/sensor-chart.component';
import { MeasurementTableComponent } from '../../../shared/components/table/measurement-table/measurement-table.component';

@Component({
  selector: 'app-sensor-detail',
  standalone: true,

  imports: [
    CommonModule,
    ButtonModule,
    CardModule,
    SensorChartComponent,
    MeasurementTableComponent
  ],

  templateUrl: './sensor-detail.component.html'
})
export class SensorDetailComponent implements OnChanges {

  @Input()
  sensor!: Sensor;

  @Output()
  back = new EventEmitter<void>();

  measurements: Measurement[] = [];

  constructor(
    private measurementService: MeasurementService
  ) { }

  ngOnChanges() {
    if (this.sensor?.id) {
      this.loadMeasurements();
    }
  }

  loadMeasurements() {
    this.measurementService
      .getMeasurements(this.sensor.id!)
      .subscribe(data => {
        this.measurements = data;
      });
  }

  loadLiveData() {
    this.measurementService
      .getRealtime(this.sensor.id!)
      .subscribe(data => {
        this.measurements = data;
      });
  }

  goBack() {
    this.back.emit();
  }

}