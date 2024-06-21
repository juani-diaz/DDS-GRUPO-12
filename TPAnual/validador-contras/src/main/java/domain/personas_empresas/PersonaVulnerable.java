package domain.personas_empresas;

import domain.vianda.ViandaRecogida;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PersonaVulnerable extends Persona {
  private LocalDate fechaRegistro;
  private EnumSituacionCalle situacionCalle;
  private String menoresACargo;
  private List<ViandaRecogida> viandasTomadas;
  private Tarjeta tarjeta;
  private List<UsoDeTarjeta> usos;
  private Integer usosRestantesPorDia = this.calcularUsos(menoresACargo);
  public void retirarVianda(Vianda viandaARecoger, Heladera heladera){
    if(usosRestantesPorDia > 0){
      usosRestantesPorDia = usosRestantesPorDia - 1;
      viandasTomadas.add(new ViandaRecogida('this', 'heladera', 'viandaARecoger', 'new Date'));
    }

  }
  public Integer calcularUsos(menoresACargo){
    return 4 + menoresACargo*2;
  }
}
