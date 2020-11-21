package servlets_jdbc.services;

import servlets_jdbc.models.CookieValue;
import servlets_jdbc.models.Person;
import servlets_jdbc.repositories.CookieRepository;
import servlets_jdbc.repositories.UserRepository;
import servlets_jdbc.services.security.PasswordEncoder;
import servlets_jdbc.services.security.models.AuthDto;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

public class LoginServiceImpl implements LoginService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public LoginServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<AuthDto> signIn(String username, String password) {
        Optional<Person> personCandidate = userRepository.findOneByUsername(username);

        if (personCandidate.isPresent()) {
            Person personInDB = personCandidate.get();
            if (passwordEncoder.verify(password, personInDB.getPassword())) {
                return Optional.of(AuthDto.from(personInDB, Collections.emptyList()));
            }
        }
        return Optional.empty();
    }
}
