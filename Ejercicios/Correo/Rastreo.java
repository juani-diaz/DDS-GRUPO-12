package Correo;

import java.sql.Date;
import java.util.List;

public class Rastreo {
   private Integer codRastreo;
   private Date fechaEnviado;
   private Date fechaRecibido;
   private List<Date> fechasEnSucursal;
   private List<String> lugaresEnviado;
}