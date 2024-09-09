import { CommonModule } from '@angular/common';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import {MatSelectModule} from '@angular/material/select';
import {Component} from '@angular/core';
import {MatRadioModule} from '@angular/material/radio';
import {MatButtonModule} from '@angular/material/button';

@Component({
  selector: 'app-colaborar-dinero',
  standalone: true,
  imports: [CommonModule, MatFormFieldModule, MatInputModule, FormsModule, MatSelectModule, MatRadioModule, MatButtonModule],
  templateUrl: './colaborar-dinero.component.html',
  styleUrl: './colaborar-dinero.component.scss'
})
export class ColaborarDineroComponent {
  cantidad: number = 0
  recu: string = '1'

  medios = [
    {value: 'visa', viewValue: 'Visa'},
    {value: 'ae', viewValue: 'American Express'},
    {value: 'm', viewValue: 'Master Card'},
    {value: 't', viewValue: 'Transferencia'},
  ]

  getViandas(){
    return Math.round(this.cantidad / 1000)
  }

  getHeladeras(){
    return Math.round(this.cantidad / 4500)
  }

  openDatePicker(dp: any) {
    dp.open();
  }

  closeDatePicker(eventData: any, dp?:any) {
    dp.close();    
  }
}
