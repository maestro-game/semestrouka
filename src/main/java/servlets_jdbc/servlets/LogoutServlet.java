package servlets_jdbc.servlets;

import servlets_jdbc.listeners.ComponentScanner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

public class LogoutServlet extends HttpServlet {

    private Properties cookieProperties;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cookieProperties = ComponentScanner.get(config, "cookieProperties", Properties.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cookie[] cookies = req.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieProperties.getProperty("cookies.userId"))) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
                break;
            }
        }

        req.getSession().setAttribute("user", null);

        resp.sendRedirect("/login");
    }
}
