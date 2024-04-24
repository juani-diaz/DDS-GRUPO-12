package com.dds12.entidad;

public class Entidad {
   private String nombre;
   private String direccion;
   private String localidad;
   private Integer codigo_postal;

   public Entidad(String nombre, String direccion, String localidad, Integer codigo_postal) {
      this.nombre = nombre;
      this.direccion = direccion;
      this.localidad = localidad;
      this.codigo_postal = codigo_postal;
   }
}