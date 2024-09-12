package domain.incidente;

import domain.rol.Tecnico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitasTecnicas {
  Tecnico tecnico;
  Incidente incidente;
  LocalDate fechaVisita;
  String descripcion;
  String foto; //TODO:definir el tipo de la foto (esta como string)
}
