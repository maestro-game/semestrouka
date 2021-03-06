package servlets_jdbc.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import servlets_jdbc.models.Person;
import servlets_jdbc.services.UserService;
import servlets_jdbc.services.security.models.AuthDto;
import servlets_jdbc.services.security.models.Role;

import javax.security.auth.message.AuthException;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

public class AuthFilter implements Filter {

    private UserService userService;

    private Properties cookieProperties;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userService = (UserService) filterConfig.getServletContext().getAttribute("userService");
        cookieProperties = (Properties) filterConfig.getServletContext().getAttribute("cookieProperties");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        HttpSession session = httpRequest.getSession();

        Object user;

        if ((user = session.getAttribute("user")) == null
                || Objects.equals(((AuthDto) user).getRole(), Role.GUEST)) {

            Cookie[] cookies;

            if ((cookies = httpRequest.getCookies()) != null) {

                boolean hasUserId = false;

                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(cookieProperties.getProperty("cookies.userId"))) {
                        if (cookie.getValue().isBlank()) {
                            break;
                        }

                        hasUserId = true;

                        Optional<Person> personCandidate;
                        try {
                            personCandidate = userService.getPersonByCookie(cookie.getValue());

                            personCandidate.ifPresent(person ->
                                    session.setAttribute("user",
                                            AuthDto.from(person, userService.getGenres(person.getUsername()))));
                        } catch (AuthException e) {
                            hasUserId = false;
                        }

                        break;
                    }
                }

                if (!hasUserId) {
                    session.setAttribute("user", AuthDto.from(
                            Person.builder()
                                    .name("Anonymous")
                                    .build(), Collections.emptyList()));
                }
            }
        }

        chain.doFilter(request, response);
    }


    @Override
    public void destroy() {
    }
}
