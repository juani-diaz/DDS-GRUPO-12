import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MigracionComponent } from './migracion.component';

describe('MigracionComponent', () => {
  let component: MigracionComponent;
  let fixture: ComponentFixture<MigracionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MigracionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MigracionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
