package servlets_jdbc.models.dto;

import servlets_jdbc.models.Person;
import servlets_jdbc.models.forms.ProfileForm;
import servlets_jdbc.services.security.models.Role;

import java.util.List;
import java.util.stream.Collectors;

public class PersonDto {
    private String username;
    private String email;
    private Role role;
    private ProfileForm personInfo;

    public PersonDto() {
    }

    public PersonDto(String username, Role role) {
        this.username = username;
        this.role = role;
    }

    public PersonDto(String username, String email, Role role, ProfileForm personInfo) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.personInfo = personInfo;
    }

    public static PersonDto from(Person person, List<String> genres) {
        return new PersonDto(person.getUsername(), person.getEmail(), person.getRole(),
                new ProfileForm(person.getUsername(), person.getName(), person.getSurname(), person.getPhone(), genres));
    }

    public static PersonDto from(Person person) {
        return new PersonDto(person.getUsername(), person.getRole());
    }

    public static List<PersonDto> from(List<Person> persons) {
        return persons.stream().map(PersonDto::from).collect(Collectors.toList());
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public ProfileForm getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(ProfileForm personInfo) {
        this.personInfo = personInfo;
    }
}
