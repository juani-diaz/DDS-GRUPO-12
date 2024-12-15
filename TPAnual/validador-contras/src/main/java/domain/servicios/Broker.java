package domain.servicios;

import domain.heladera.EnumMotivoApertura;
import domain.heladera.Heladera;
import domain.heladera.PedidoApertura;
import domain.heladera.SensorDeTemperatura;
import domain.registro.RegistroTemperatura;
import domain.rol.Tarjeta;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Broker {
    private String server;
    private List<Heladera> heladeras;

    //con pegarle a estos metodos medianta a un server se puede administrar temperaturas y sacar viandas

    public void setTemperatura(String nombreHeladera, Float temperatura) {
        Optional<domain.heladera.Heladera> heladera= heladeras.stream().filter(heladera1 -> Objects.equals(heladera1.getNombre(), nombreHeladera)).findFirst();
        heladera.ifPresent(h -> {
            SensorDeTemperatura sensor = new SensorDeTemperatura(h);
            RegistroTemperatura registroSensor = new RegistroTemperatura();
            registroSensor.setFecha(new Date());
            registroSensor.setTemperatura(temperatura);
            sensor.nuevoRegistro(registroSensor);
        });

        if (!heladera.isPresent()) {
            throw new IllegalArgumentException("Heladera no encontrada: " + nombreHeladera);
        }
    }

    public void abrirHeladera(String nombreHeladera, EnumMotivoApertura motivo,Tarjeta tarjeta) {
        Optional<domain.heladera.Heladera> heladera= heladeras.stream().filter(heladera1 -> Objects.equals(heladera1.getNombre(), nombreHeladera)).findFirst();
        heladera.ifPresent(h -> {
            PedidoApertura pedido= new PedidoApertura();
            pedido.setHeladera(h);
            pedido.setMotivo(motivo);
            pedido.setTarjeta(tarjeta);
        });
        if (!heladera.isPresent()) {
            throw new IllegalArgumentException("Heladera no encontrada: " + nombreHeladera);
        }
    }


}