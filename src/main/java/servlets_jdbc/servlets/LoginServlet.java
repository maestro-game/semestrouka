package servlets_jdbc.servlets;

import servlets_jdbc.listeners.ComponentScanner;
import servlets_jdbc.models.Person;
import servlets_jdbc.services.CookieService;
import servlets_jdbc.services.LoginService;
import servlets_jdbc.services.security.GeneralValidator;
import servlets_jdbc.services.security.models.AuthDto;
import servlets_jdbc.services.security.models.ErrorMapPair;
import servlets_jdbc.services.security.models.Role;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class LoginServlet extends HttpServlet {

    private LoginService loginService;
    private CookieService cookieService;
    private GeneralValidator generalValidator;
    private Properties cookieProperties;

    @Override
    public void init(ServletConfig config) throws ServletException {
        loginService = ComponentScanner.get(config, "loginService", LoginService.class);
        cookieService = ComponentScanner.get(config, "cookieService", CookieService.class);
        generalValidator = ComponentScanner.get(config, "generalValidator", GeneralValidator.class);
        cookieProperties = ComponentScanner.get(config, "cookieProperties", Properties.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ErrorMapPair<Boolean, Map<String, String>> parameters = generalValidator.validate(req.getParameterMap(),
                "name", 4, "password", 8);

        Map<String, String> resultMap = parameters.getMap();

        if (parameters.hasErrors()) {
            req.setAttribute("errors", resultMap);
            doGet(req, resp);
        } else {
            boolean rememberMe = resultMap.get("rememberMe") != null;

            Optional<AuthDto> userCandidate;
            if ((userCandidate =
                    loginService.signIn(resultMap.get("username"), resultMap.get("password"))).isPresent()) {

                if (rememberMe) {
                    Cookie cookie = new Cookie(cookieProperties.getProperty("cookies.userId"),
                            cookieService.registerCookie(userCandidate.get().getUsername()));
                    cookie.setMaxAge(10 * 365 * 24 * 60 * 60);
                    resp.addCookie(cookie);
                } else {
                    req.getSession().setAttribute("user", userCandidate.get());
                }
                resp.sendRedirect("/profile");
            } else {
                resultMap.clear();
                resultMap.put("42", "Username or password is wrong");
                req.setAttribute("errors", resultMap);
                doGet(req, resp);
            }
        }
    }
}
