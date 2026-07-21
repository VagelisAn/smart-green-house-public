import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SystemStatusCardComponent } from './system-status-card.component';

describe('SystemStatusCardComponent', () => {
  let component: SystemStatusCardComponent;
  let fixture: ComponentFixture<SystemStatusCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SystemStatusCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SystemStatusCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
