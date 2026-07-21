export interface Weather {
    location: string;
    temperature: number;
    feelsLike?: number;
    humidity: number;
    windSpeed: number;
    weatherCode?: number;
    weatherDescription: string;
    timestamp: number;
  }