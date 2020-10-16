package servlets_jdbc.services;

import servlets_jdbc.models.Person;

import java.util.Optional;

public interface LoginService {
    Optional<String> signIn(Person person);
}
