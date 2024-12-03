package domain.heladera;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;

public class AltaHeladeraController implements Handler {

  private RepoHeladera hela;

  public AltaHeladeraController(RepoHeladera hela) {
    super();
    this.hela = hela;
  }

  @Override
  public void handle(Context ctx) throws Exception {
    Heladera prod = ctx.bodyAsClass(Heladera.class);
    this.hela.add(prod);
    ctx.status(HttpStatus.CREATED);
    ctx.result("Producto agregado correctamente");
  }
}
