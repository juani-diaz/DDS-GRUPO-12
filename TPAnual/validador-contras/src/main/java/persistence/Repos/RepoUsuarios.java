package persistence.Repos;

import domain.auth.Usuario;
import domain.persona.MedioDeContacto;
import lombok.Getter;
import persistence.BDUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RepoUsuarios extends BDUtils {
    @Getter
    private List<Usuario> usuarios = new ArrayList<>();

    private static RepoUsuarios instance;

    private RepoUsuarios(){
        this.usuarios = getAll_Usuarios_BD();
    }

    public static RepoUsuarios getInstance() {
        if(instance == null) {
            instance = new RepoUsuarios();
        }
        return instance;
    }

    EntityManager em = getEm();

    public void add_Usuario(Usuario usuario) {
        this.usuarios.add(usuario);

        comenzarTransaccion(em);

        if(usuario.getRol().getPersona().getDocumento()!=null) {
            em.persist(usuario.getRol().getPersona().getDocumento());
        }
        em.persist(usuario.getRol().getPersona());
        em.persist(usuario.getRol());
        em.persist(usuario);
        if(usuario.getRol().getPersona().getMediosDeContacto()!=null) {
            for (MedioDeContacto m : usuario.getRol().getPersona().getMediosDeContacto()) {
                em.persist(m);
            }
        }

        commit(em);
    }

    public void remove_Usuario(Usuario usuario) {
        usuarios.remove(usuario);

        comenzarTransaccion(em);

        try {
            this.em.remove(usuario);
            commit(em);
        } catch (Exception e) {
            System.out.println("Error al remover el USUARIO: " + usuario + e);
        }

    }
    public void update_Usuario(Usuario usuario) {
        comenzarTransaccion(em);

        try {
            usuario = this.em.merge(usuario);  // Asegura que el objeto esté gestionado
            commit(em);
        } catch (Exception e) {
            rollback(em);
            System.out.println("Error al actualizar el USUARIO: " + usuario + e);
        }
    }


    public Usuario findById_Usuario(Integer usuarioID) {
        Usuario usuario = null;

        System.out.println(usuarioID);

        try {
            usuario = usuarios.stream().filter(u -> u.getId() == usuarioID).findFirst().get();
        } catch (Exception var4) {
            System.out.println("Error al buscar usuario: " + usuarioID);
        }

        return usuario;
    }

    public Usuario findByUsuario(String usu) {
        Usuario usuario = null;

        System.out.println(usuarios);

        try {
            usuario = usuarios.stream().filter(u -> Objects.equals(u.getUsuario(), usu)).findFirst().get();
        } catch (Exception var4) {
            System.out.println("Error al buscar usuario: " + usu);
        }

        return usuario;
    }

    public List<Usuario> getAll_Usuarios_BD() {
        CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
        CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
        Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);
        criteriaQuery.select(usuarioRoot);
        Query query = this.em.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
