export interface Sensor {
    id?: number;
    name: string;
    type: string;
    model: string;
    location: string;
    online:boolean;
    lastMeasurement?:Date;
  }