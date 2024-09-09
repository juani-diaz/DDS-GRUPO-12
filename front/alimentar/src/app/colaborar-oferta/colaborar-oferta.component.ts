import { Component, inject } from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { NuevaOfertaComponent } from '../dialogos/nueva-oferta/nueva-oferta.component';

@Component({
  selector: 'app-colaborar-oferta',
  standalone: true,
  imports: [MatButtonModule, MatIconModule],
  templateUrl: './colaborar-oferta.component.html',
  styleUrl: './colaborar-oferta.component.scss'
})
export class ColaborarOfertaComponent {
  readonly dialog = inject(MatDialog);

  ofertasNuevas: any[] = []

  openDialog(): void {
    const dialogRef = this.dialog.open(NuevaOfertaComponent, {
      data: {},
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) {
        this.ofertasNuevas.push(result)
      }
    });
  }
}
