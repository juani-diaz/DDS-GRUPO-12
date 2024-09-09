import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private tipoUsuario!: string;

  constructor() {
  }

  getTipoUsuario() {
    return this.tipoUsuario
  }

  setTipoUsuario(tipoUsuario: string) {
    this.tipoUsuario = tipoUsuario
  }
}
