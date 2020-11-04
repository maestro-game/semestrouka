package servlets_jdbc.models.dto;

import servlets_jdbc.models.Film;

import java.util.List;
import java.util.stream.Collectors;

public class FilmDto {
    private final Long id;
    private final String name;
    private final Film.Description description;

    public FilmDto(Long id, String name, Film.Description description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static FilmDto from(Film film) {
        return new FilmDto(film.getId(), film.getName(), film.getDescription());
    }

    public static List<FilmDto> from(List<Film> films) {
        return films.stream().map(FilmDto::from).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Film.Description getDescription() {
        return description;
    }
}
