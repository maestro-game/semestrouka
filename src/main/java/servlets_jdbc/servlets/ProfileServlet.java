package servlets_jdbc.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import servlets_jdbc.listeners.ComponentScanner;
import servlets_jdbc.models.forms.ProfileInfo;
import servlets_jdbc.services.UserService;
import servlets_jdbc.services.security.SecurityChecker;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ProfileServlet extends HttpServlet {

    private UserService userService;
    private SecurityChecker securityChecker;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = ComponentScanner.get(config, "userService", UserService.class);
        securityChecker = ComponentScanner.get(config, "securityChecker", SecurityChecker.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (securityChecker.isAuthorized(req)) {
            ProfileInfo profileInfo = userService.getInfo(securityChecker.getUser(req).getUsername());

            req.setAttribute("profileInfo", profileInfo);
        }
        req.getRequestDispatcher("profile.ftl").forward(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        ProfileInfo profileInfo = mapper.readValue(req.getReader().readLine(), ProfileInfo.class);
        String username;
        if (securityChecker.isValidUsername(req, username = profileInfo.getUsername())) {
            try (PrintWriter pw = resp.getWriter()) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                pw.print(mapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(userService.updateInfo(profileInfo)));
                pw.flush();
            }
        } else {
            throw new IllegalArgumentException("That's not your username: " + username);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        ProfileInfo profileInfo = mapper.readValue(req.getReader().readLine(), ProfileInfo.class);
        String username;
        if (securityChecker.isValidUsername(req, username = profileInfo.getUsername())) {
            try (PrintWriter pw = resp.getWriter()) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                pw.print(mapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(userService.deleteInfo(profileInfo)));
                pw.flush();
            }
        } else {
            throw new IllegalArgumentException("That's not your username: " + username);
        }
    }
}
