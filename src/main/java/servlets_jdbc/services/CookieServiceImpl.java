package servlets_jdbc.services;

import servlets_jdbc.models.CookieValue;
import servlets_jdbc.repositories.CookieRepository;

import java.util.UUID;

public class CookieServiceImpl implements CookieService {

    private final CookieRepository cookieRepository;

    public CookieServiceImpl(CookieRepository cookieRepository) {
        this.cookieRepository = cookieRepository;
    }

    @Override
    public String registerCookie(String username) {
        CookieValue cookieValue = CookieValue.builder()
                .value(UUID.randomUUID().toString())
                .user(username)
                .build();
        cookieRepository.save(cookieValue);
        return cookieValue.getValue();
    }
}
