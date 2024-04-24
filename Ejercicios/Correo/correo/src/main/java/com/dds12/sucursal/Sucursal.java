package com.dds12.sucursal;

import com.dds12.empleado.Empleado;
import com.dds12.empleado.EnumEmpleado;
import com.dds12.envio.Envio;

import java.util.Date;
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
      envioRecibido.getRastreo_envio().agregarParada(new Date(), "Sucursal "+id_sucursal+" de "+localidad+" - "+domicilio);
      envios.add(envioRecibido);
   }

   public void avanzarEnvio(int codigo_rastreo, String localidad) {
      for(Envio e : envios){
         if(e.getCodigo_rastreo() == codigo_rastreo){
            e.getRastreo_envio().getFechas_en_sucursal().add(new Date());
            e.getRastreo_envio().getLugares_enviado().add(localidad);
            return;
         }
      }
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

   public List<Envio> getEnvios() {
      return envios;
   }
}