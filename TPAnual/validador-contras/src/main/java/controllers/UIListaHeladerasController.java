package controllers;


import java.util.HashMap;
import java.util.Map;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;

import domain.heladera.RepoHeladera;

public class UIListaHeladerasController implements Handler{
  private RepoHeladera hela;

  public UIListaHeladerasController(RepoHeladera hela) {
    super();
    this.hela = hela;
  }

  @Override
  public void handle(Context ctx) throws Exception {
    Map<String, Object> model = new HashMap<>();

    model.put("hela", hela.findById(7));
    ctx.render("vianda.hbs", model);
  }

}
