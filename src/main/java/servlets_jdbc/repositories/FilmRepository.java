package servlets_jdbc.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import servlets_jdbc.models.Film;

import java.util.List;
import java.util.Optional;

public interface FilmRepository {
    Optional<List<Film>> findAllFilms();

    Optional<Film> saveFilm(Film film);

    Optional<Film> findFilmById(Long filmId);

    Optional<List<Film>> findAllFilms(Film.Description description) throws JsonProcessingException;

    Optional<List<Film>> findAllFilmsByNameStartsWith(String name);
}
