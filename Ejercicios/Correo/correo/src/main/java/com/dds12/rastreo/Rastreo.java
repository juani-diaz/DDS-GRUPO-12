package com.dds12.rastreo;

import java.util.Date;
import java.util.List;

public class Rastreo {
   private Integer codigo_rastreo;
   private Date fecha_enviado;
   private Date fecha_recibido;
   private List<Date> fechas_en_sucursal;
   private List<String> lugares_enviado;

   public Rastreo(Integer codigo_rastreo, Date fecha_enviado, Date fecha_recibido, List<Date> fechas_en_sucursal, List<String> lugares_enviado) {
      this.codigo_rastreo = codigo_rastreo;
      this.fecha_enviado = fecha_enviado;
      this.fecha_recibido = fecha_recibido;
      this.fechas_en_sucursal = fechas_en_sucursal;
      this.lugares_enviado = lugares_enviado;
   }

   public void agregarParada(Date fecha, String lugar) {
      fechas_en_sucursal.add(fecha);
      lugares_enviado.add(lugar);
   }

   public List<Date> getFechas_en_sucursal() {
      return fechas_en_sucursal;
   }

   public List<String> getLugares_enviado() {
      return lugares_enviado;
   }

   public Date getFecha_recibido() {
      return fecha_recibido;
   }

   public void setFecha_recibido(Date fecha_recibido) {
      this.fecha_recibido = fecha_recibido;
   }
}