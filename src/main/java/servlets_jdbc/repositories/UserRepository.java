package servlets_jdbc.repositories;

import servlets_jdbc.models.Person;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    int save(Person person);

    Optional<Person> findOneByUsername(String name);

    List<Person> findAll();
}
