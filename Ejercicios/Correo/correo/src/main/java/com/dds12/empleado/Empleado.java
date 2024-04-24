package com.dds12.empleado;

public class Empleado {
   private String nombre;
   private String apellido;
   private Integer id_empleado;

   public EnumEmpleado getTipo_empleado() {
      return tipo_empleado;
   }

   private EnumEmpleado tipo_empleado;

   public Empleado(String nombre, String apellido, Integer id_empleado, EnumEmpleado tipo_empleado) {
      this.nombre = nombre;
      this.apellido = apellido;
      this.id_empleado = id_empleado;
      this.tipo_empleado = tipo_empleado;
   }

   public String getNombre() {
      return nombre;
   }
}