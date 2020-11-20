package servlets_jdbc.servlets;

import servlets_jdbc.listeners.ComponentScanner;
import servlets_jdbc.models.Film;
import servlets_jdbc.services.FilmService;
import servlets_jdbc.services.Json;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FilterFilmsServlet extends HttpServlet {

    private FilmService filmService;
    private Json json;

    @Override
    public void init(ServletConfig config) throws ServletException {
        filmService = ComponentScanner.get(config, "filmService", FilmService.class);
        json = ComponentScanner.get(config, "json", Json.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("filter.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Film.Description description = json.read(req, Film.Description.class);

        var res = filmService.filter(description).getFilms();

        json.write(resp, res);
    }
}
