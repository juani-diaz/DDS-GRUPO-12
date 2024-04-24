package com.dds12.envio;

import com.dds12.entidad.Entidad;
import com.dds12.rastreo.Rastreo;
import com.dds12.empleado.Empleado;

public class Envio {
   private Entidad remitente;
   private Entidad destinatario;
   private Number precio;
   private Integer codigo_rastreo;
   private Rastreo rastreo_envio;
   private Empleado cartero;
}