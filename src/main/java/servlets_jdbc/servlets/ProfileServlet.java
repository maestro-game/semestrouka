package servlets_jdbc.servlets;

import servlets_jdbc.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileServlet extends HttpServlet {
    private UserService userService;

//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        userService = (UserService) config.getServletContext().getAttribute("userService");
//    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("profile.ftl").forward(req, resp);
    }
}
