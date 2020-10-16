package servlets_jdbc.services.security;

public interface PasswordEncoder {
    String encode(String password);

    boolean verify(String password, String passwordHashed);
}
