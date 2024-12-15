package domain.contra;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter @Getter

public class Usuario  {

  private String nombreUsuario;

  private String contraseña;

  private List<Requisitos> chequeos = new ArrayList<>();

  //TODO que sea automatico al crearse


  private void iniciarRequisitos(List<Requisitos> req){
    chequeos.addAll(req);
  }
  public void crearUsuario(String contrasena, String usuario , List<Requisitos> requi){
    this.iniciarRequisitos(requi);
    this.nombreUsuario = usuario;
    if(chequeos.stream().allMatch(requisitos-> requisitos.evaluarContrasena(contrasena))){
      this.contraseña=contrasena;
    }
  }

}