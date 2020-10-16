package servlets_jdbc.services;

import servlets_jdbc.models.CookieValue;
import servlets_jdbc.models.Person;
import servlets_jdbc.repositories.CookieRepository;
import servlets_jdbc.repositories.UserRepository;
import servlets_jdbc.services.security.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

public class LoginServiceImpl implements LoginService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final CookieRepository cookieRepository;

    public LoginServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, CookieRepository cookieRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.cookieRepository = cookieRepository;
    }

    @Override
    public Optional<String> signIn(Person person) {
        Optional<Person> personCandidate = userRepository.findOneByUsername(person.getUsername());

        if (personCandidate.isPresent()) {
            Person personInDB = personCandidate.get();
            if (passwordEncoder.verify(person.getPassword(), personInDB.getPassword())) {
                CookieValue cookieValue = CookieValue.builder()
                        .value(UUID.randomUUID().toString())
                        .user(personInDB.getUsername())
                        .build();
                cookieRepository.save(cookieValue);
                return Optional.of(cookieValue.getValue());
            }
        }
        return Optional.empty();
    }
}
