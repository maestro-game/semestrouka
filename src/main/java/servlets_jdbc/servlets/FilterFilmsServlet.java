package servlets_jdbc.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import servlets_jdbc.listeners.ComponentScanner;
import servlets_jdbc.models.Film;
import servlets_jdbc.services.FilmService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FilterFilmsServlet extends HttpServlet {

    private FilmService filmService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        filmService = ComponentScanner.get(config, "filmService", FilmService.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Film.Description description = new ObjectMapper().readValue(req.getReader(), Film.Description.class);

//        var res = filmService.filter(description).getFilms();

        try (PrintWriter pw = resp.getWriter()) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            pw.print(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(description));
            pw.flush();
        }
    }
}
