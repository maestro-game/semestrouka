package servlets_jdbc.models.dto;

import servlets_jdbc.models.Person;

import java.util.List;
import java.util.stream.Collectors;

public class PersonDto {

    private String username;

    public PersonDto(String username) {
        this.username = username;
    }

    public static PersonDto from(Person person) {
        return new PersonDto(person.getUsername());
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
}
