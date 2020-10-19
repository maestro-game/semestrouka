package servlets_jdbc.services.security;

import servlets_jdbc.models.dto.PersonDto;
import servlets_jdbc.services.security.models.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class SecurityChecker {
    public boolean checkRole(HttpServletRequest req, Enum<Role> role) {
        return checkRole(req, role.name());
    }

    public boolean checkRole(HttpServletRequest req, String s) {
        return Objects.equals(((PersonDto) req.getSession().getAttribute("user")).getRole().name(), s);
    }
}
