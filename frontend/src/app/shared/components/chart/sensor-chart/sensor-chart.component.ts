import {
  Component,
  Input,
  OnChanges
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { Measurement } from '../../../../core/models/measurement';

import { ChartModule } from 'primeng/chart';
import { CardModule } from 'primeng/card';


@Component({
  selector: 'app-sensor-chart',
  standalone: true,
  imports: [
    CommonModule,
    ChartModule,
    CardModule
  ],
  templateUrl: './sensor-chart.component.html'
})
export class SensorChartComponent implements OnChanges {

  @Input()
  measurements: Measurement[] = [];
  @Input()
  sensorName: string = 'Measurements';

  chartData: any;
  chartOptions: any;

  ngOnChanges() {
    if (this.measurements.length) {
      this.createChart();
    }
  }

  private createChart() {
    const values =
      this.measurements.map(
        m => m.value
      );
    const labels =
      this.measurements.map(m => {
        const date =
          new Date(m.timestamp);
        return (
          date.toLocaleDateString(
            'el-GR',
            {
              day: '2-digit',
              month: '2-digit'
            }
          )
          +
          ' '
          +
          date.toLocaleTimeString(
            'el-GR',
            {
              hour: '2-digit',
              minute: '2-digit'
            }
          )
        );
      });
    this.chartData = {
      labels,
      datasets: [
        {
          label: this.sensorName,
          data: values,
          fill: true,
          tension: 0.4,
          borderColor: '#34d399',
          backgroundColor:
            'rgba(52,211,153,0.1)'
        }
      ]
    };

    this.chartOptions = {
      plugins: {
        legend: {
          labels: {
            color: '#abcdef'
          }
        }
      },
      scales: {
        x: {
          ticks: {
            color: '#a1a1aa'
          }
        },
        y: {
          ticks: {
            color: '#a1a1aa'
          }
        }
      }
    };
  }
}