package domain.auth;

import domain.persona.Persona;
import domain.persona.PersonaFisica;
import domain.persona.PersonaJuridica;
import domain.rol.Administrador;
import domain.rol.Colaborador;
import domain.rol.Rol;
import domain.rol.Tecnico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.EntidadPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Usuario extends EntidadPersistente {

    @Column(unique = true)
    private String usuario;

    @Column
    private String contra;

    @OneToOne
    private Rol rol;

    public boolean puedeAcceder(PermisosMetodo ruta){
        Class<? extends Rol> tipoRol = rol.getClass();
        if(tipoRol == Colaborador.class){
            Persona persona = rol.getPersona();
            if(persona.getClass() == PersonaFisica.class){
                return ruta.isVFisica();
            } else if (persona.getClass() == PersonaJuridica.class) {
                return ruta.isVOrganizacion();
            }
        } else if (tipoRol == Tecnico.class){
                return ruta.isVTecnico();
        } else if (tipoRol == Administrador.class) {
                return ruta.isVAdministrador();
        }
        return false;
    }
}
