package servlets_jdbc.services;

import servlets_jdbc.models.Film;
import servlets_jdbc.repositories.FilmRepository;

import java.util.Collections;
import java.util.List;

public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public List<Film> getFilms() {
        return filmRepository.findAllFilms().orElse(Collections.emptyList());
    }

    @Override
    public Film addFilm(Film film) {
        return filmRepository.saveFilm(film).orElseThrow(
                () -> new IllegalArgumentException("Film can't be saved")
        );
    }
}
