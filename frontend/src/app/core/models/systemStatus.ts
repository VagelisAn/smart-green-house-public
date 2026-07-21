export interface SystemStatus {
    activeAlerts: number;
    lastUpdate: Date | null;
    mqttOnline: boolean;
    offlineSensors: number;
    onlineSensors: number;
    totalSensors: number;
}