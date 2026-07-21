import {
  Component,
  Input
} from '@angular/core';
import {
  TableModule
} from 'primeng/table';
import {
  CommonModule
} from '@angular/common';
import { Measurement } from '../../../../core/models/measurement';


@Component({
  selector: 'app-measurement-table',
  standalone: true,
  imports: [
    CommonModule,
    TableModule
  ],
  templateUrl:
    './measurement-table.component.html'

})
export class MeasurementTableComponent {
  @Input()
  measurements: Measurement[] = [];
}