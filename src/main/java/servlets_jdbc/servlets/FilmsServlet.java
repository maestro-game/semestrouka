package servlets_jdbc.servlets;

import servlets_jdbc.listeners.ComponentScanner;
import servlets_jdbc.models.Film;
import servlets_jdbc.models.dto.FilmDto;
import servlets_jdbc.services.FilmService;
import servlets_jdbc.services.security.SecurityChecker;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static servlets_jdbc.services.security.models.Role.ADMIN;

public class FilmsServlet extends HttpServlet {

    private FilmService filmService;
    private SecurityChecker securityChecker;

    @Override
    public void init(ServletConfig config) throws ServletException {
        filmService = ComponentScanner.get(config, "filmService", FilmService.class);
        securityChecker = ComponentScanner.get(config, "securityCheck", SecurityChecker.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getAttribute("films") == null) {
            req.setAttribute("films", FilmDto.from(filmService.getFilms()));
        }

        req.getRequestDispatcher("mainfilms.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (securityChecker.checkRole(req, ADMIN)) {
            req.setAttribute(
                    "newFilm",
                    filmService.addFilm(
                            new Film(req.getParameter("name"),
                                    new Film.Description(
                                            req.getParameter("imgSrc"),
                                            Integer.parseInt(req.getParameter("year")),
                                            splitBy(req.getParameter("genres")),
                                            splitBy(req.getParameter("actors")),
                                            splitBy(req.getParameter("awards"))
                                    )
                            )
                    )
            );
        }

        doGet(req, resp);
    }

    private List<String> splitBy(String str, String regex) {
        return List.of(str.split(regex));
    }

    private List<String> splitBy(String str) {
        return splitBy(str, "\\s");
    }
}
