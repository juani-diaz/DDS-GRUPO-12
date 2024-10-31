import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-puntos',
  standalone: true,
  imports: [MatButtonModule, MatIconModule],
  templateUrl: './puntos.component.html',
  styleUrl: './puntos.component.scss'
})
export class PuntosComponent {
  puntos = 400;

  menos(){
    this.puntos -= 400
  }

  mas(){
    this.puntos += 400
  }
}
