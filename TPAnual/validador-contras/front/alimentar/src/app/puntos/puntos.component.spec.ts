import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PuntosComponent } from './puntos.component';

describe('PuntosComponent', () => {
  let component: PuntosComponent;
  let fixture: ComponentFixture<PuntosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PuntosComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PuntosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
