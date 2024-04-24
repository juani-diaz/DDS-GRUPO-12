package com.dds12.envio.carta;

import com.dds12.empleado.Empleado;
import com.dds12.entidad.Entidad;
import com.dds12.envio.Envio;
import com.dds12.rastreo.Rastreo;

public class Carta extends Envio{
   private EnumSellado sellado;
   private EnumCarta tipo_carta;

   public Carta(Entidad remitente, Entidad destinatario, Number precio, Integer codigo_rastreo, Rastreo rastreo_envio, Empleado cartero, EnumSellado sellado, EnumCarta tipo_carta) {
      super(remitente, destinatario, precio, codigo_rastreo, rastreo_envio, cartero);
      this.sellado = sellado;
      this.tipo_carta = tipo_carta;
   }
}
