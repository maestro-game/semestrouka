package servlets_jdbc.servlets;

import servlets_jdbc.listeners.ComponentScanner;
import servlets_jdbc.models.dto.PersonDto;
import servlets_jdbc.services.UserService;
import servlets_jdbc.services.security.SecurityChecker;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

import static servlets_jdbc.services.security.models.Role.ADMIN;

public class UsersServlet extends HttpServlet {

    private UserService userService;
    private SecurityChecker securityChecker;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = ComponentScanner.get(config, "userService", UserService.class);
        securityChecker = ComponentScanner.get(config, "securityChecker", SecurityChecker.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (securityChecker.checkRole(req, ADMIN)) {
            req.setAttribute("users", userService.getAll().orElseGet(Collections::emptyList)
                    .stream().map(PersonDto::getUsername).collect(Collectors.toList()));
        }
        req.getRequestDispatcher("users.ftl").forward(req, resp);
    }
}
