import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';

import { SensorService } from '../../../core/services/sensor.service';
import { Sensor } from '../../../core/models/sensor';

@Component({
  selector:'app-add-sensor-dialog',
  standalone:true,
  imports:[
    CommonModule,
    FormsModule,
    DialogModule,
    ButtonModule,
    InputTextModule
  ],
  templateUrl:'./add-sensor-dialog.component.html'
})
export class AddSensorDialogComponent {

  visible=false;

  newSensor:Sensor={
    name:'',
    model:'',
    type:'',
    location:'',
    online: false
  };

  @Output()
  saved=new EventEmitter<void>();

  constructor(private sensorService:SensorService){}

  open(){
    this.newSensor={
      name:'',
      model:'',
      type:'',
      location:'',
      online: false
    };
    this.visible=true;
  }

  saveSensor(){
    this.sensorService
    .save(this.newSensor)
    .subscribe(()=>{
      this.visible=false;
      this.saved.emit();
    });
  }
}