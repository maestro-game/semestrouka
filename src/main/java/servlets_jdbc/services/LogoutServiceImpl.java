package servlets_jdbc.services;

import servlets_jdbc.repositories.CookieRepository;

public class LogoutServiceImpl implements LogoutService {

    private final CookieRepository cookieRepository;

    public LogoutServiceImpl(CookieRepository cookieRepository) {
        this.cookieRepository = cookieRepository;
    }

    @Override
    public void logout(String value) {
        cookieRepository.delete(value);
    }
}
