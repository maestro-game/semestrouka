package servlets_jdbc.services;

import servlets_jdbc.models.Person;

public interface SignUpService {
    int signUp(Person person);
}
