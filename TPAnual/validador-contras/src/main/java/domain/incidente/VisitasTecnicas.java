package domain.incidente;

import domain.rol.Tecnico;

import java.time.LocalDate;

public class VisitasTecnicas {
  Tecnico tecnico;
  Incidente incidente;
  LocalDate fechaVisita;
  String descripcion;
  String foto; //TODO:definir el tipo de la foto (esta como string)
}
