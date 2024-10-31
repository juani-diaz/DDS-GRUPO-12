import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LinkNavegacionComponent } from './link-navegacion.component';

describe('LinkNavegacionComponent', () => {
  let component: LinkNavegacionComponent;
  let fixture: ComponentFixture<LinkNavegacionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LinkNavegacionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LinkNavegacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
