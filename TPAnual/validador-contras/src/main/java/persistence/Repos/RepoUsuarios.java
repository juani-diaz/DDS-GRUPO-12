package persistence.Repos;

import domain.auth.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import lombok.NoArgsConstructor;
import persistence.BDUtils;

@NoArgsConstructor
public class RepoUsuarios {
    private EntityManager em = BDUtils.getEntityManager();

    public void add_Usuario(Usuario usuario) {
        BDUtils.comenzarTransaccion(this.em);

        try {
            this.em.persist(usuario);
            BDUtils.commit(this.em);
        } catch (Exception e) {
            System.out.println("Error al agregar el USUARIO: " + usuario + e);
        }

    }

    public void remove_Usuario(Usuario usuario) {
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
            usuario = (Usuario)this.em.getReference(Usuario.class, usuarioID);
        } catch (Exception var4) {
            System.out.println("Error al buscar usuario: " + usuarioID);
        }

        return usuario;
    }

    public Usuario findByUsuario(String usu) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);
        Root<Usuario> root = cq.from(Usuario.class);
        cq.where(cb.equal(root.get("usuario"), usu));

        try {
            return (Usuario)this.em.createQuery(cq).getSingleResult();
        } catch (Exception var6) {
            return null;
        }
    }

    public List<Usuario> getAll_Usuarios() {
        CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
        CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
        Root<Usuario> usuarioRoot = criteriaQuery.from(Usuario.class);
        criteriaQuery.select(usuarioRoot);
        Query query = this.em.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
