import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ColaborarOfertaComponent } from './colaborar-oferta.component';

describe('ColaborarOfertaComponent', () => {
  let component: ColaborarOfertaComponent;
  let fixture: ComponentFixture<ColaborarOfertaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ColaborarOfertaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ColaborarOfertaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
