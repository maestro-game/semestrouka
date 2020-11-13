package servlets_jdbc.services;

import servlets_jdbc.models.Person;
import servlets_jdbc.models.dto.PersonDto;
import servlets_jdbc.models.forms.ProfileForm;

import javax.security.auth.message.AuthException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<List<PersonDto>> getAll();

    Optional<Person> getPersonByCookie(String value) throws AuthException;

    ProfileForm updateInfo(ProfileForm userInfo);

    List<String> getGenres(String username);

    ProfileForm deleteInfo(ProfileForm userInfo);
}
