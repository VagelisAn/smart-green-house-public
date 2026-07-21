import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SensorPageComponent } from './sensor-page.component';

describe('SensorPageComponent', () => {
  let component: SensorPageComponent;
  let fixture: ComponentFixture<SensorPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SensorPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SensorPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
