package servlets_jdbc.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import servlets_jdbc.models.Film;
import servlets_jdbc.models.rowmappers.RowMapper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class FilmRepositoryImpl implements FilmRepository {

    //  language=sql
    private static final String Q_FIND_ALL_FILMS = "SELECT * FROM films;";
    //  language=sql
    private static final String Q_SAVE_FILM = "INSERT INTO films (name, description) " +
            "VALUES (?, ?::jsonb) ;";

    private final JdbcUtil jdbcUtil;
    private final RowMapper<Film> filmRowMapper = resultSet ->
    {
        try {
            return new Film(resultSet.getString("name"),
                    new ObjectMapper().readValue(
                            resultSet.getString("description"),
                            Film.Description.class)
            );
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    };

    public FilmRepositoryImpl(JdbcUtil jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    @Override
    public Optional<List<Film>> findAllFilms() {
        return Optional.of(jdbcUtil.findAll(Q_FIND_ALL_FILMS, filmRowMapper));
    }

    @Override
    public Optional<Film> saveFilm(Film film) {
        Optional<Film> res = Optional.of(film);
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            Film.Description description = film.getDescription();
            jdbcUtil.update(Q_SAVE_FILM, film.getName(), ow.writeValueAsString(description));
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(e);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
        return res;
    }
}
