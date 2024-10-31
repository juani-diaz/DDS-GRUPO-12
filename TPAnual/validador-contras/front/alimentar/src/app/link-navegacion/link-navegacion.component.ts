import { Component, input } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'link-navegacion',
  standalone: true,
  imports: [RouterModule, MatIconModule, CommonModule],
  templateUrl: './link-navegacion.component.html',
  styleUrl: './link-navegacion.component.scss'
})
export class LinkNavegacionComponent {
  titulo = input.required<string>();
  ruta = input.required<string>();
  icono = input.required<string>();

  constructor(private router: Router){
    
  }

  linkActivo(){
    return this.ruta() == this.router.url;
  }
}
