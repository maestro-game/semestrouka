package servlets_jdbc.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import servlets_jdbc.listeners.ComponentScanner;
import servlets_jdbc.models.forms.ProfileForm;
import servlets_jdbc.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ProfileServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = ComponentScanner.get(config, "userService", UserService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("profile.ftl").forward(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper;

        try (PrintWriter pw = resp.getWriter()) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            pw.print((mapper = new ObjectMapper()).writerWithDefaultPrettyPrinter().writeValueAsString(
                    userService.updateInfo(mapper.readValue(req.getParameter("userInfo"), ProfileForm.class))
            ));
            pw.flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper;

        try (PrintWriter pw = resp.getWriter()) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            pw.print((mapper = new ObjectMapper()).writerWithDefaultPrettyPrinter().writeValueAsString(
                    userService.deleteInfo(mapper.readValue(req.getParameter("userInfo"), ProfileForm.class))
            ));
            pw.flush();
        }
    }
}
