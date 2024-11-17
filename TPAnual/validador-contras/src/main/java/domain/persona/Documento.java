package domain.persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
//@Entity
//@Embeddable
public class Documento implements Serializable {;

  //@Id
  private String tipo;
  //@Id
  private String numero;
}