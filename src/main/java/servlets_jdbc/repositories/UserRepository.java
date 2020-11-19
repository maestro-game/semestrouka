package servlets_jdbc.repositories;

import servlets_jdbc.models.Person;
import servlets_jdbc.models.forms.ProfileInfo;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    int save(Person person);

    Optional<Person> findOneByUsername(String name);

    List<Person> findAll();

    ProfileInfo updateInfo(ProfileInfo userInfo, boolean toDelete);

    Optional<List<String>> getGenresByUsername(String username);
}
