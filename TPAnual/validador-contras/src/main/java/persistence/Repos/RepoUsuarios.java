package persistence.Repos;

import domain.auth.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import domain.persona.MedioDeContacto;
import lombok.NoArgsConstructor;
import persistence.BDUtils;

public class RepoUsuarios {
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

    private EntityManager em = BDUtils.getEntityManager();

    public void add_Usuario(Usuario usuario) {
        this.usuarios.add(usuario);

        BDUtils.comenzarTransaccion(this.em);

        try {
            em.persist(usuario.getRol().getPersona().getDocumento());
            em.persist(usuario.getRol().getPersona());
            em.persist(usuario.getRol());
            em.persist(usuario);

            BDUtils.commit(this.em);
        } catch (Exception e) {
            System.out.println("Error al agregar el USUARIO: " + usuario + e);
        }

    }

    public void remove_Usuario(Usuario usuario) {
        usuarios.remove(usuario);

        BDUtils.comenzarTransaccion(this.em);

        try {
            this.em.remove(usuario);
            BDUtils.commit(this.em);
        } catch (Exception e) {
            System.out.println("Error al remover el USUARIO: " + usuario + e);
        }

    }

    public Usuario findById_Usuario(Integer usuarioID) {
        Usuario usuario = null;

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
