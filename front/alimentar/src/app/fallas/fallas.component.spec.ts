import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FallasComponent } from './fallas.component';

describe('FallasComponent', () => {
  let component: FallasComponent;
  let fixture: ComponentFixture<FallasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FallasComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FallasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
