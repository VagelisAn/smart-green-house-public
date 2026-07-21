import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSensorDialogComponent } from './add-sensor-dialog.component';

describe('AddSensorDialogComponent', () => {
  let component: AddSensorDialogComponent;
  let fixture: ComponentFixture<AddSensorDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddSensorDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddSensorDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
