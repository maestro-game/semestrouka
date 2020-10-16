package servlets_jdbc.servlets;

import servlets_jdbc.models.dto.PersonDto;
import servlets_jdbc.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

public class UsersServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", userService.getAll().orElseGet(Collections::emptyList)
                .stream().map(PersonDto::getUsername).collect(Collectors.toList()));
        req.getRequestDispatcher("users.ftl").forward(req, resp);
    }
}
