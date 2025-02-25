package views;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.NoArgsConstructor;
import persistence.Repos.RepoHeladera;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class UI_Landing implements Handler {

    private Map<String, Object> model = new HashMap();

    @Override
    public void handle(Context ctx) throws Exception {
        model.put("conteo", "237.000");

        model.put("hela", RepoHeladera.getInstance().getHeladeras());

        ctx.render("landing.hbs", this.model);
    }
}
