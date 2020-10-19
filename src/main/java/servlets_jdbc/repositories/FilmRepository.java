package servlets_jdbc.repositories;

import servlets_jdbc.models.Film;

import java.util.List;
import java.util.Optional;

public interface FilmRepository {
    Optional<List<Film>> findAllFilms();

    Optional<Film> saveFilm(Film film);
}
