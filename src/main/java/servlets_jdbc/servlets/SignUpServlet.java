package servlets_jdbc.servlets;

import servlets_jdbc.listeners.ComponentScanner;
import servlets_jdbc.models.Person;
import servlets_jdbc.services.SignUpService;
import servlets_jdbc.services.security.GeneralValidator;
import servlets_jdbc.services.security.PasswordEncoder;
import servlets_jdbc.services.security.models.ErrorMapPair;
import servlets_jdbc.services.security.models.Role;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class SignUpServlet extends HttpServlet {

    private SignUpService signUpService;
    private PasswordEncoder passwordEncoder;
    private GeneralValidator generalValidator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        signUpService = ComponentScanner.get(config, "signUpService", SignUpService.class);
        passwordEncoder = ComponentScanner.get(config, "passwordEncoder", PasswordEncoder.class);
        generalValidator = ComponentScanner.get(config, "generalValidator", GeneralValidator.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("signUp.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        ErrorMapPair<Boolean, Map<String, String>> parameters = generalValidator.validate(req.getParameterMap(),
                "name", 4, "password", 8);

        if (parameters.hasErrors()) {
            req.setAttribute("errors", parameters.getMap());
            doGet(req, resp);
        } else {
            Map<String, String> resultMap = parameters.getMap();
            try {
                signUpService.signUp(Person.builder()
                        .username(resultMap.get("username").toLowerCase())
                        .password(passwordEncoder.encode(resultMap.get("password")))
                        .email(resultMap.get("email"))
                        .name(resultMap.get("name"))
                        .surname(resultMap.get("surname"))
                        .phone(resultMap.get("phone"))
                        .role(Enum.valueOf(Role.class, "USER"))
                        .build());
                resp.sendRedirect("/login");
            } catch (IllegalStateException e) {
                resultMap.clear();
                resultMap.put("42", "Username is already in use");
                req.setAttribute("errors", resultMap);
                doGet(req, resp);
            } catch (IllegalArgumentException e) {
                resultMap.clear();
                resultMap.put("42", "Email is not valid");
                req.setAttribute("errors", resultMap);
                doGet(req, resp);
            }
        }
    }
}
