import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeladerasComponent } from './heladeras.component';

describe('HeladerasComponent', () => {
  let component: HeladerasComponent;
  let fixture: ComponentFixture<HeladerasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeladerasComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HeladerasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
