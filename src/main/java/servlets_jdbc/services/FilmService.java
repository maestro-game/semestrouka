package servlets_jdbc.services;

import servlets_jdbc.models.Film;

import java.util.List;

public interface FilmService {
    List<Film> getFilms();

    Film addFilm(Film film);
}
