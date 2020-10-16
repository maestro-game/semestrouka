package servlets_jdbc.models.dto;

import servlets_jdbc.models.Person;
import servlets_jdbc.services.security.models.Role;

import java.util.List;
import java.util.stream.Collectors;

public class PersonDto {
    private String username;

    private Role role;

    public PersonDto() {
    }

    public PersonDto(String username, Role role) {
        this.username = username;
        this.role = role;
    }

    public static PersonDto from(Person person) {
        return new PersonDto(person.getUsername(), person.getRole());
    }

    public static List<PersonDto> from(List<Person> persons) {
        return persons.stream().map(PersonDto::from).collect(Collectors.toList());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
