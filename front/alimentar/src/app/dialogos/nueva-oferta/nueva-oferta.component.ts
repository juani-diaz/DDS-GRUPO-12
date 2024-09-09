import { Component, inject, model } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import {CdkTextareaAutosize, TextFieldModule} from '@angular/cdk/text-field';

export class DialogData {
  titulo: string;
  descripcion: string;
  puntos: number;

  constructor(titulo: string, descripcion: string, puntos: number) {
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.puntos = puntos;
  }
}

@Component({
  selector: 'app-nueva-oferta',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    MatButtonModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    MatDialogClose,
    CdkTextareaAutosize,
    TextFieldModule
  ],
  templateUrl: './nueva-oferta.component.html',
  styleUrl: './nueva-oferta.component.scss'
})
export class NuevaOfertaComponent {
  readonly dialogRef = inject(MatDialogRef<NuevaOfertaComponent>);
  readonly data = inject<DialogData>(MAT_DIALOG_DATA);
  readonly titulo = model(this.data.titulo);
  readonly descripcion = model(this.data.descripcion);
  readonly puntos = model(this.data.puntos);

  onNoClick(): void {
    this.dialogRef.close();
  }

  getData(){
    return new DialogData(this.titulo(), this.descripcion(), this.puntos());
  }
}
