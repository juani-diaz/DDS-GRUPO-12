import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ColaborarHeladeraComponent } from './colaborar-heladera.component';

describe('ColaborarHeladeraComponent', () => {
  let component: ColaborarHeladeraComponent;
  let fixture: ComponentFixture<ColaborarHeladeraComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ColaborarHeladeraComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ColaborarHeladeraComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
