package com.dds12.envio.telegrama;

import com.dds12.empleado.Empleado;
import com.dds12.entidad.Entidad;
import com.dds12.envio.Envio;
import com.dds12.rastreo.Rastreo;

public class Telegrama extends Envio{
   private String texto;
   private EnumTelegrama clase_telegrama;

   public Telegrama(Entidad remitente, Entidad destinatario, Number precio, Integer codigo_rastreo, Rastreo rastreo_envio, Empleado cartero, String texto, EnumTelegrama clase_telegrama) {
      super(remitente, destinatario, precio, codigo_rastreo, rastreo_envio, cartero);
      this.texto = texto;
      this.clase_telegrama = clase_telegrama;
   }
}
