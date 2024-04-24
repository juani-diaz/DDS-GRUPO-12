package com.dds12.rastreo;

import java.sql.Date;
import java.util.List;

public class Rastreo {
   private Integer codigo_rastreo;
   private Date fecha_enviado;
   private Date fecha_recibido;
   private List<Date> fechas_en_sucursal;
   private List<String> lugares_enviado;
}