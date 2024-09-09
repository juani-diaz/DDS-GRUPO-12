import { CommonModule } from '@angular/common';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import {MatSelectModule} from '@angular/material/select';
import {MatRadioModule} from '@angular/material/radio';
import {MatButtonModule} from '@angular/material/button';
import {Component} from '@angular/core';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {provideNativeDateAdapter} from '@angular/material/core';
import {MatCheckboxModule} from '@angular/material/checkbox';

@Component({
  selector: 'app-colaborar-registro',
  standalone: true,
  providers: [provideNativeDateAdapter()],
  imports: [CommonModule, MatFormFieldModule, MatInputModule, FormsModule, MatSelectModule, MatRadioModule, MatButtonModule, MatDatepickerModule, MatCheckboxModule],
  templateUrl: './colaborar-registro.component.html',
  styleUrl: './colaborar-registro.component.scss'
})
export class ColaborarRegistroComponent {
  calle: boolean = true;
  docu: string = "na";

  documentos = [
    {value: 'na', viewValue: 'No tiene'},
    {value: 'dni', viewValue: 'DNI'},
    {value: 'libreta', viewValue: 'Libreta'},
    {value: 'o', viewValue: 'Otro'},
  ]

}
