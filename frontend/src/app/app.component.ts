import { Component, OnInit } from '@angular/core';

// PrimeNG Modules
import { SensorPageComponent } from './features/sensors/sensor-page/sensor-page.component';
import { WebsocketService } from './core/websocket/websocket.service';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { NotificationService } from './core/services/notification.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    ToastModule,
    SensorPageComponent
  ],
  providers: [
    MessageService
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(
    private notificationService: NotificationService,
    private messageService: MessageService,
    private websocketService: WebsocketService
  ) { }

  ngOnInit() {
    console.log('🚀 AppComponent initialized');
    this.websocketService.connect();
    this.websocketService.sensors$
      .subscribe(notification => {
        console.log('📩 Notification received in AppComponent:',
          notification
        );
        this.messageService.add({
          severity:
            notification.type.includes('ALERT')
              ? 'error'
              : 'info',
          summary:
            notification.type,
          detail:
            notification.message
        });
        console.log('✅ Toast sent to MessageService');
      });
  }
}