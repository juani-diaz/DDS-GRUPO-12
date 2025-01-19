//import org.junit.jupiter.api.Test;
//import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
//import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;
//import persistence.BDUtils;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//
//import javax.persistence.EntityManager;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class ContextTest extends AbstractPersistenceTest implements WithGlobalEntityManager {
//
//  @Test
//  public void contextUp() {
//    EntityManager em = BDUtils.getEntityManager();
//    BDUtils.comenzarTransaccion(em);
//    assertNotNull(entityManager());
//  }
//
//  @Test
//  public void contextUpWithTransaction() throws Exception {
//    withTransaction(() -> {});
//  }
//}