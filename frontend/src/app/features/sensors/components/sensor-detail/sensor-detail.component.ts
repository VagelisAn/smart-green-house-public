import { Component, Input } from '@angular/core';
import { Measurement } from '../../../../core/models/measurement';
import { MeasurementService } from '../../../../core/services/measurement.service';
import { Sensor } from '../../../../core/models/sensor';

@Component({
  selector: 'app-sensor-detail',
  imports: [],
  templateUrl: './sensor-detail.component.html',
  styleUrl: './sensor-detail.component.css'
})
export class SensorDetailComponent {

  @Input()
  sensor!: Sensor;
  measurements: Measurement[] = [];

  constructor(
    private measurementService: MeasurementService
  ){}

  ngOnChanges(){
    if(this.sensor?.id){
      this.loadMeasurements();
    }
  }

  loadMeasurements(){
    this.measurementService
    .getMeasurements(this.sensor.id!)
    .subscribe(data=>{
        this.measurements = data;
    });
  }
}
