package servlets_jdbc.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import servlets_jdbc.listeners.ComponentScanner;
import servlets_jdbc.models.Actor;
import servlets_jdbc.models.dto.ActorDto;
import servlets_jdbc.services.ActorService;
import servlets_jdbc.services.security.SecurityChecker;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import static servlets_jdbc.services.security.models.Role.ADMIN;

public class ActorsServlet extends HttpServlet {

    private ActorService actorService;
    private SecurityChecker securityChecker;

    @Override
    public void init(ServletConfig config) throws ServletException {
        actorService = ComponentScanner.get(config, "actorService", ActorService.class);
        securityChecker = ComponentScanner.get(config, "securityChecker", SecurityChecker.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("actors", ActorDto.from(actorService.getActors()));

        req.getRequestDispatcher("actors.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper;
        Map<String, String[]> map;

        if (securityChecker.checkRole(req, ADMIN)) {
            req.setAttribute(
                    "newActor",
                    actorService.addActor(Actor.builder()
                            .fullname((map = new HashMap<>(req.getParameterMap())).remove("fullname")[0])
                            .description(
                                    (mapper = new ObjectMapper()).readValue(mapper.writeValueAsString(
                                            Map.ofEntries(
                                                    map.entrySet().stream()
                                                            .map((e) -> new AbstractMap.SimpleEntry<>(
                                                                    e.getKey(),
                                                                    Array.get(e.getValue(), 0)))
                                                            .distinct().toArray(Map.Entry[]::new))),
                                            Actor.Description.class)).build())
            );
        }

        doGet(req, resp);
    }
}
