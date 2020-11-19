package servlets_jdbc.services.security.models;

import servlets_jdbc.models.Person;

import java.util.List;

public class AuthDto {
    private final String username;
    private final Role role;
    private final List<String> genres;


    public AuthDto(String username, Role role, List<String> genres) {
        this.username = username;
        this.role = role;
        this.genres = genres;
    }

    public static AuthDto from(Person person, List<String> genres) {
        return new AuthDto(person.getUsername(), person.getRole(), genres);
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public List<String> getGenres() {
        return genres;
    }
}
