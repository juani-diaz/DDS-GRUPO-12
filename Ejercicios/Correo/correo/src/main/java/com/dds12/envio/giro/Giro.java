package com.dds12.envio.giro;

import com.dds12.empleado.Empleado;
import com.dds12.entidad.Entidad;
import com.dds12.envio.Envio;
import com.dds12.rastreo.Rastreo;

public class Giro extends Envio{
   private Number importe;

   public Giro(Entidad remitente, Entidad destinatario, Number precio, Integer codigo_rastreo, Rastreo rastreo_envio, Empleado cartero, Number importe) {
      super(remitente, destinatario, precio, codigo_rastreo, rastreo_envio, cartero);
      this.importe = importe;
   }
}
