package servlets_jdbc.services;

import servlets_jdbc.models.Person;
import servlets_jdbc.repositories.UserRepository;

public class SignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;

    public SignUpServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public int signUp(Person person) {
        return userRepository.save(person);
    }

}
