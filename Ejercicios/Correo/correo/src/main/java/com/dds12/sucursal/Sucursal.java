package com.dds12.sucursal;

import com.dds12.empleado.Empleado;
import com.dds12.envio.Envio;

import java.util.List;

public class Sucursal {
   private Integer id_sucursal;
   private String domicilio;
   private String localidad;
   private List<Empleado> empleados;
   private List<Envio> envios;

   public void realizarEnvio(Envio envioRecibido) {
      envios.add(envioRecibido);
   }
}