package servlets_jdbc.servlets;

import servlets_jdbc.listeners.ComponentScanner;
import servlets_jdbc.models.Actor;
import servlets_jdbc.services.ActorService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ActorServlet extends HttpServlet {

    private ActorService actorService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        actorService = ComponentScanner.get(config, "actorService", ActorService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long actorId = Long.valueOf(req.getParameter("actorId"));

        Actor actor = actorService.getActorById(actorId);

        req.setAttribute("actor", actor);

        req.getRequestDispatcher("actor.ftl").forward(req, resp);
    }
}
