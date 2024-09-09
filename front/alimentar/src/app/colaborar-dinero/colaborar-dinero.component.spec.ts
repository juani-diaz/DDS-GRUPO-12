import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ColaborarDineroComponent } from './colaborar-dinero.component';

describe('ColaborarDineroComponent', () => {
  let component: ColaborarDineroComponent;
  let fixture: ComponentFixture<ColaborarDineroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ColaborarDineroComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ColaborarDineroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
