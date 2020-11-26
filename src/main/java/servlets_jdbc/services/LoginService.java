package servlets_jdbc.services;

import servlets_jdbc.models.Person;
import servlets_jdbc.services.security.models.AuthDto;

import java.util.Optional;

public interface LoginService {
    Optional<AuthDto> signIn(String username, String password);
}
