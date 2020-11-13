package servlets_jdbc.models.forms;

import java.util.List;

public class ProfileForm {
    private String username;
    private String name;
    private String surname;
    private String phone;
    private List<String> genres;

    public ProfileForm() {
    }

    public ProfileForm(String username, String name, String surname, String phone, List<String> genres) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.genres = genres;
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
}
