import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ColaborarRegistroComponent } from './colaborar-registro.component';

describe('ColaborarRegistroComponent', () => {
  let component: ColaborarRegistroComponent;
  let fixture: ComponentFixture<ColaborarRegistroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ColaborarRegistroComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ColaborarRegistroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
