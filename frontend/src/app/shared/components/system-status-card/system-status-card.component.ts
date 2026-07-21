import { Component, OnInit } from '@angular/core';
import { SystemStatusService } from '../../../core/services/system-status.service';
import { CardModule } from 'primeng/card';
import { CommonModule } from '@angular/common';
import { TagModule } from 'primeng/tag';
import { SystemStatus } from '../../../core/models/systemStatus';


@Component({
  selector: 'app-system-status-card',
  standalone:true,
  imports:[
    CommonModule,
    CardModule,
    TagModule
  ],
  templateUrl:'./system-status-card.component.html'
})
export class SystemStatusCardComponent implements OnInit{
  status?: SystemStatus;
  constructor(
    private systemStatusService:SystemStatusService
  ){}

  ngOnInit(): void {
    this.loadStatus();
  }
  loadStatus(){
    this.systemStatusService.getStatus()
    .subscribe({
      next:(data)=>{
        this.status=data;
      },
      error:(err)=>{
        console.error(
          'System status error',
          err
        );
      }
    });
  }
}