package servlets_jdbc.services;

import servlets_jdbc.models.Person;
import servlets_jdbc.models.dto.PersonDto;
import servlets_jdbc.models.forms.ProfileInfo;

import javax.security.auth.message.AuthException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<List<PersonDto>> getAll();

    Optional<Person> getPersonByCookie(String value) throws AuthException;

    ProfileInfo updateInfo(ProfileInfo userInfo);

    List<String> getGenres(String username);

    ProfileInfo deleteInfo(ProfileInfo userInfo);

    ProfileInfo getInfo(String username);
}
