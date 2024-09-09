import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ColaborarViandaComponent } from './colaborar-vianda.component';

describe('ColaborarViandaComponent', () => {
  let component: ColaborarViandaComponent;
  let fixture: ComponentFixture<ColaborarViandaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ColaborarViandaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ColaborarViandaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
