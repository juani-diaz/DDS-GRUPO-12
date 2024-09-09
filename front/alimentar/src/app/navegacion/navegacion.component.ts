import { Component } from '@angular/core';
import { LinkNavegacionComponent } from '../link-navegacion/link-navegacion.component'
import { UsuarioService } from '../usuario.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'navegacion',
  standalone: true,
  imports: [LinkNavegacionComponent, CommonModule],
  templateUrl: './navegacion.component.html',
  styleUrl: './navegacion.component.scss'
})
export class NavegacionComponent {
  menu = [
    new ElementoNavegacion('Colaboraciones', '/colaborar', 'volunteer_activism', [
      new ElementoNavegacion('Dinero', '/colaborar/dinero', 'payments', [], ['H', 'J']),
      new ElementoNavegacion('Donar vianda', '/colaborar/vianda', 'soup_kitchen', [], ['H']),
      new ElementoNavegacion('Mover vianda', '/colaborar/mover_vianda', 'takeout_dining', [], ['H']),
      new ElementoNavegacion('Registrar persona', '/colaborar/registro', 'person_raised_hand', [], ['H']),
      new ElementoNavegacion('Heladeras', '/colaborar/heladera', 'kitchen', [], ['H', 'J']),
      new ElementoNavegacion('Ofertas', '/colaborar/oferta', 'category', [], ['J']),
    ], ['H', 'J']),
    new ElementoNavegacion('Fallas', '/fallas', 'feedback', [], ['H']),
    new ElementoNavegacion('Puntos', '/puntos', 'redeem', [], ['H', 'J']),
    new ElementoNavegacion('Migración', '/migracion', 'upload', [], ['A']),
    new ElementoNavegacion('Gestión Heladeras', '/heladeras', 'kitchen', [], ['A']),
    new ElementoNavegacion('Reportes', '/reportes', 'format_list_numbered', [], ['A']),
  ];

  constructor(private usuarioService: UsuarioService){
    usuarioService.setTipoUsuario('H')
  }

  mostrar(e: ElementoNavegacion){
    return e.accesiblePor.includes(this.usuarioService.getTipoUsuario());
  }
}

class ElementoNavegacion {
  titulo: string;
  ruta: string;
  icono: string;
  hijos: ElementoNavegacion[];
  accesiblePor: string[]

  constructor(titulo: string, ruta: string, icono: string, hijos: ElementoNavegacion[], accesiblePor: string[]){
    this.titulo = titulo;
    this.ruta = ruta;
    this.icono = icono;
    this.hijos = hijos;
    this.accesiblePor = accesiblePor;
  }
}
