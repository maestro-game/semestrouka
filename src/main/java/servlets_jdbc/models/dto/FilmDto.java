package servlets_jdbc.models.dto;

import servlets_jdbc.models.Film;

import java.util.List;
import java.util.stream.Collectors;

public class FilmDto {

    private final String name;

    private final Film.Description description;

    public FilmDto(String name, Film.Description description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Film.Description getDescription() {
        return description;
    }

    public static FilmDto from(Film film) {
        return new FilmDto(film.getName(), film.getDescription());
    }

    public static List<FilmDto> from(List<Film> films) {
        return films.stream().map(FilmDto::from).collect(Collectors.toList());
    }
}
