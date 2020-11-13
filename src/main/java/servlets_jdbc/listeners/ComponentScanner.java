package servlets_jdbc.listeners;

import servlets_jdbc.repositories.*;
import servlets_jdbc.services.*;
import servlets_jdbc.services.security.GeneralValidator;
import servlets_jdbc.services.security.PasswordEncoder;
import servlets_jdbc.services.security.PasswordEncoderBCryptImpl;
import servlets_jdbc.services.security.SecurityChecker;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComponentScanner implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {

//        ==== PROPERTIES ====

        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("cookie.properties"));
        } catch (IOException e) {
            throw new IllegalArgumentException("Wrong properties filename", e);
        }

        sce.getServletContext().setAttribute("cookieProperties", properties);

//        ==== /PROPERTIES ====


//        ==== HELPERS ====

        JdbcUtil jdbcUtil = new JdbcUtil();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        PasswordEncoder passwordEncoder = new PasswordEncoderBCryptImpl();
        GeneralValidator generalValidator = new GeneralValidator();
        SecurityChecker securityChecker = new SecurityChecker();
        FilterValidator filterValidator = new FilterValidator();

        sce.getServletContext().setAttribute("jdbcUtil", jdbcUtil);
        sce.getServletContext().setAttribute("executorService", executorService);
        sce.getServletContext().setAttribute("passwordEncoder", passwordEncoder);
        sce.getServletContext().setAttribute("generalValidator", generalValidator);
        sce.getServletContext().setAttribute("securityChecker", securityChecker);

//        ==== /HELPERS ====


//        ==== REPOSITORIES ====

        UserRepository userRepository = new UserRepositoryImpl(jdbcUtil);
        CookieRepository cookieRepository = new CookieRepositoryImpl(jdbcUtil);
        FilmRepository filmRepository = new FilmRepositoryImpl(jdbcUtil);
        ReviewRepository reviewRepository = new ReviewRepositoryImpl(jdbcUtil);
        ActorRepository actorRepository = new ActorRepositoryImpl(jdbcUtil);

//        ==== /REPOSITORIES ====


//        ==== SERVICES ====

        UserService userService = new UserServiceImpl(userRepository, cookieRepository);
        LoginService loginService = new LoginServiceImpl(passwordEncoder, userRepository, cookieRepository);
        LogoutService logoutService = new LogoutServiceImpl(cookieRepository);
        SignUpService signUpService = new SignUpServiceImpl(userRepository);
        FilmService filmService = new FilmServiceImpl(filmRepository, reviewRepository, filterValidator);
        ActorService actorService = new ActorServiceImpl(actorRepository);

        sce.getServletContext().setAttribute("userService", userService);
        sce.getServletContext().setAttribute("loginService", loginService);
        sce.getServletContext().setAttribute("logoutService", logoutService);
        sce.getServletContext().setAttribute("signUpService", signUpService);
        sce.getServletContext().setAttribute("filmService", filmService);
        sce.getServletContext().setAttribute("actorService", actorService);

//        ==== /SERVICES ====

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ((JdbcUtil) sce.getServletContext().getAttribute("jdbcUtil")).close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static <T> T get(ServletConfig cfg, String componentName, Class<T> className) {
        return className.cast(cfg.getServletContext().getAttribute(componentName));
    }

}

