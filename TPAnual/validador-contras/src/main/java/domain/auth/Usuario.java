package domain.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import domain.rol.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.EntidadPersistente;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Usuario extends EntidadPersistente {

    @Column(unique = true)
    private String usuario;

    @Column
    private String contra;

    @OneToOne
    private Rol rol;
}
