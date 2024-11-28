package controllers;

import domain.heladera.RepoHeladera;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;

public class ListaHeladeraController implements Handler{
  private RepoHeladera hela;

  public ListaHeladeraController(RepoHeladera hela) {
    super();
    this.hela = hela;
  }

  @Override
  public void handle(Context ctx) throws Exception {
    ctx.json(hela.findById(4L));
  }
}
