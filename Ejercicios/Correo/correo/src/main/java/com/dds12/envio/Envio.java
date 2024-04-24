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

   public Envio(Entidad remitente, Entidad destinatario, Number precio, Integer codigo_rastreo, Rastreo rastreo_envio, Empleado cartero) {
      this.remitente = remitente;
      this.destinatario = destinatario;
      this.precio = precio;
      this.codigo_rastreo = codigo_rastreo;
      this.rastreo_envio = rastreo_envio;
      this.cartero = cartero;
   }

   public Envio() {

   }

   public Rastreo getRastreo_envio() {
      return rastreo_envio;
   }

   public Integer getCodigo_rastreo() {
      return codigo_rastreo;
   }

   public Empleado getCartero() {
      return cartero;
   }

   public Entidad getDestinatario() {
      return destinatario;
   }
}