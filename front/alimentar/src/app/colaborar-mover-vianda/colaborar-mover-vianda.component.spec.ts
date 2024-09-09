import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ColaborarMoverViandaComponent } from './colaborar-mover-vianda.component';

describe('ColaborarMoverViandaComponent', () => {
  let component: ColaborarMoverViandaComponent;
  let fixture: ComponentFixture<ColaborarMoverViandaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ColaborarMoverViandaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ColaborarMoverViandaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
