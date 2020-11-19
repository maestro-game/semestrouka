package servlets_jdbc.services.security;

import servlets_jdbc.services.security.models.AuthDto;
import servlets_jdbc.services.security.models.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class SecurityChecker {
    public boolean checkRole(HttpServletRequest req, Enum<Role> role) {
        return checkRole(req, role.name());
    }

    public boolean checkRole(HttpServletRequest req, String s) {
        return Objects.equals(getUser(req).getRole().name(), s);
    }

    public boolean isAuthorized(HttpServletRequest req) {
        return !checkRole(req, Role.GUEST);
    }

    public AuthDto getUser(HttpServletRequest req) {
        return (AuthDto) req.getSession().getAttribute("user");
    }

    public boolean isValidUsername(HttpServletRequest req, String username) {
        return username.equals(getUser(req).getUsername());
    }
}
