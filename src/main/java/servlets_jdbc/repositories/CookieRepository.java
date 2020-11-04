package servlets_jdbc.repositories;

import servlets_jdbc.models.CookieValue;

public interface CookieRepository {
    int save(CookieValue cookieValue);

    String findUsernameByValue(String value);

    void delete(String value);
}
