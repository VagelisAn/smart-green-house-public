import { Injectable } from "@angular/core";
import { NotificationMessage } from "../models/notification";
import { Subject } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private notificationSubject =
    new Subject<NotificationMessage>();

  notifications$ =
    this.notificationSubject.asObservable();

  show(notification: NotificationMessage) {
    this.notificationSubject.next(notification);
  }
}

