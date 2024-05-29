package domain.persona_contra;
import java.util.ArrayList;
import java.util.List;

import lombok.Setter;
import lombok.Getter;


@Setter @Getter

public class Usuario  {

  private String nombreUsuario;

  private String contraseña;

  private List<Requisitos> chequeos = new ArrayList<>();



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