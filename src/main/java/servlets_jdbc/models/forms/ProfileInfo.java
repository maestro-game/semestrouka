package servlets_jdbc.models.forms;

import servlets_jdbc.models.Person;

import java.util.List;

public class ProfileInfo {
    private String username;
    private String email;
    private String name;
    private String surname;
    private String phone;
    private List<String> genres;

    public ProfileInfo() {
    }

    public ProfileInfo(String username, String email, String name, String surname, String phone, List<String> genres) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.genres = genres;
    }

    public static ProfileInfo from(Person person, List<String> genres) {
        return new ProfileInfo(person.getUsername(), person.getEmail(),
                person.getName(), person.getSurname(), person.getPhone(), genres);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean containGenres() {
        return genres != null;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
