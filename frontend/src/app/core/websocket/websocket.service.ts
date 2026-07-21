import { Injectable } from '@angular/core';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import { Subject } from 'rxjs';
import { NotificationMessage } from '../models/notification';


@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private client!: Client;

  private sensorsSubject =
      new Subject<NotificationMessage>();

  sensors$ =
      this.sensorsSubject.asObservable();

  connect() {
    this.client = new Client({
      webSocketFactory: () =>
        new SockJS('/api/ws'),
      reconnectDelay: 5000,
      debug: msg => {
        console.log('STOMP:', msg);
      },
      onConnect: () => {
        console.log('✅ CONNECTED');
        this.client.subscribe(
          '/topic/sensors',
          message => {
            const data =
              JSON.parse(message.body);
            console.log("DATA:", data);
            // εδώ πάει στο Angular
            this.sensorsSubject.next(data);
          }
        );
      }
    });
    this.client.activate();
  }
}