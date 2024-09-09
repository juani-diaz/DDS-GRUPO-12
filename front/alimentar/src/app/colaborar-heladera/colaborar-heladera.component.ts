import { Component } from '@angular/core';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-colaborar-heladera',
  standalone: true,
  imports: [MatCheckboxModule, MatFormFieldModule, MatInputModule, FormsModule],
  templateUrl: './colaborar-heladera.component.html',
  styleUrl: './colaborar-heladera.component.scss'
})
export class ColaborarHeladeraComponent {
  heladeras = [
    {nombre: "UTN Medrano", direccion: "Av. Medrano 1000", capacidad: 30, sus: false},
    {nombre: "YPF 2", direccion: "Av. Rivadavia 6767", capacidad: 60, sus: false},
    {nombre: "Ejército de Salvación Almagro", direccion: "Av. Rivadavia 3255", capacidad: 20, sus: false},
    {nombre: "Farmacity 31", direccion: "Nogoyá 4568", capacidad: 15, sus: false}
  ]
}
