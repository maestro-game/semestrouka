package servlets_jdbc.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import servlets_jdbc.models.Person;
import servlets_jdbc.repositories.UserRepository;
import servlets_jdbc.services.security.PasswordEncoder;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SignUpServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void testSignUp() {
        Person person = Person.builder().email("a@b.c").username("Vasya").password(passwordEncoder.encode("vasyavasya")).build();

        new SignUpServiceImpl(userRepository)
                .signUp(person);

        verify(userRepository).save(person);
    }

}
