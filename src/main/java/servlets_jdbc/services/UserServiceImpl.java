package servlets_jdbc.services;

import servlets_jdbc.models.Person;
import servlets_jdbc.models.dto.PersonDto;
import servlets_jdbc.models.forms.ProfileInfo;
import servlets_jdbc.repositories.CookieRepository;
import servlets_jdbc.repositories.UserRepository;

import javax.security.auth.message.AuthException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CookieRepository cookieRepository;

    public UserServiceImpl(UserRepository userRepository, CookieRepository cookieRepository) {
        this.userRepository = userRepository;
        this.cookieRepository = cookieRepository;
    }

    @Override
    public Optional<List<PersonDto>> getAll() {
        return Optional.of(PersonDto.from(userRepository.findAll()));
    }

    @Override
    public Optional<Person> getPersonByCookie(String value) throws AuthException {
        try {
            return userRepository.findOneByUsername(cookieRepository.findUsernameByValue(value));
        } catch (NullPointerException e) {
            throw new AuthException("Not authorized");
        }
    }

    @Override
    public ProfileInfo updateInfo(ProfileInfo userInfo) {
        return userRepository.updateInfo(userInfo, false);
    }

    @Override
    public List<String> getGenres(String username) {
        return userRepository.getGenresByUsername(username).orElse(Collections.emptyList());
    }

    @Override
    public ProfileInfo deleteInfo(ProfileInfo userInfo) {
        return userRepository.updateInfo(userInfo, true);
    }

    @Override
    public ProfileInfo getInfo(String username) {
        Person person = userRepository.findOneByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("No such user")
        );
        return ProfileInfo.from(person, userRepository.getGenresByUsername(username).orElse(Collections.emptyList()));
    }

}
