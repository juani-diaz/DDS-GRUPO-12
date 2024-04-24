package com.dds12.sucursal;

import com.dds12.empleado.Empleado;
import com.dds12.empleado.EnumEmpleado;
import com.dds12.envio.Envio;

import java.util.List;

public class Sucursal {
   private Integer id_sucursal;
   private String domicilio;
   private String localidad;
   private List<Empleado> empleados;
   private List<Envio> envios;

   public Sucursal(Integer id_sucursal, String domicilio, String localidad, List<Empleado> empleados, List<Envio> envios) {
      this.id_sucursal = id_sucursal;
      this.domicilio = domicilio;
      this.localidad = localidad;
      this.empleados = empleados;
      this.envios = envios;
   }

   public void realizarEnvio(Envio envioRecibido) {
      envios.add(envioRecibido);
   }

   // primer cartero que haya
   public Empleado asignarCartero() {
      for(Empleado e: empleados) {
         if(e.getTipo_empleado() == EnumEmpleado.CARTERO){
            return e;
         }
      }
      return null;
   }
}