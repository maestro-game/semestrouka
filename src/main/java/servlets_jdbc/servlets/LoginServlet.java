package servlets_jdbc.servlets;

import servlets_jdbc.models.Person;
import servlets_jdbc.services.LoginService;
import servlets_jdbc.services.security.GeneralValidator;
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

    private GeneralValidator generalValidator;

    private Properties cookieProperties;

    @Override
    public void init(ServletConfig config) throws ServletException {
        loginService = (LoginService) config.getServletContext().getAttribute("loginService");
        generalValidator = (GeneralValidator) config.getServletContext().getAttribute("generalValidator");
        cookieProperties = (Properties) config.getServletContext().getAttribute("cookieProperties");
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
            Person person = Person.builder()
                    .username(resultMap.get("username").toLowerCase())
                    .password(resultMap.get("password"))
                    .role(Enum.valueOf(Role.class, "USER"))
                    .build();

            Optional<String> cookieCandidate = loginService.signIn(person);


            if (cookieCandidate.isPresent()) {
                Cookie cookie = new Cookie(cookieProperties.getProperty("cookies.userId"), cookieCandidate.get());
                resp.addCookie(cookie);
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