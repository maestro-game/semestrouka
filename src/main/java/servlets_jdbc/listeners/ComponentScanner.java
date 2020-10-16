package servlets_jdbc.listeners;

import com.zaxxer.hikari.HikariDataSource;
import servlets_jdbc.repositories.*;
import servlets_jdbc.services.*;
import servlets_jdbc.services.security.GeneralValidator;
import servlets_jdbc.services.security.PasswordEncoder;
import servlets_jdbc.services.security.PasswordEncoderBCryptImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComponentScanner implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("cookie.properties"));
        } catch (IOException e) {
            throw new IllegalArgumentException("Wrong name", e);
        }

        JdbcUtil jdbcUtil = new JdbcUtil();

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        PasswordEncoder passwordEncoder = new PasswordEncoderBCryptImpl();

        GeneralValidator generalValidator = new GeneralValidator();

        UserRepository userRepository = new UserRepositoryImpl(jdbcUtil);

        CookieRepository cookieRepository = new CookieRepositoryImpl(jdbcUtil);

        UserService userService = new UserServiceImpl(userRepository, cookieRepository);

        LoginService loginService = new LoginServiceImpl(passwordEncoder, userRepository, cookieRepository);

        SignUpService signUpService = new SignUpServiceImpl(userRepository);

        sce.getServletContext().setAttribute("cookieProperties", properties);

        sce.getServletContext().setAttribute("jdbcUtil", jdbcUtil);

        sce.getServletContext().setAttribute("passwordEncoder", passwordEncoder);
        sce.getServletContext().setAttribute("generalValidator", generalValidator);
        sce.getServletContext().setAttribute("executorService", executorService);

        sce.getServletContext().setAttribute("signUpService", signUpService);
        sce.getServletContext().setAttribute("loginService", loginService);
        sce.getServletContext().setAttribute("userService", userService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ((JdbcUtil) sce.getServletContext().getAttribute("jdbcUtil")).close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}

