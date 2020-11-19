package servlets_jdbc.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    //  language=sql
    private static final String Q_FIND_BY_ID = "SELECT * FROM films WHERE id = ? LIMIT 1;";
    //  language=sql
    private static final String Q_FIND_ALL_FILMS_BY_DESCRIPTION = "SELECT * FROM films" +
            " WHERE description @> ?::jsonb ;";

    private final JdbcUtil jdbcUtil;
    private final RowMapper<Film> filmRowMapper = resultSet ->
    {
        try {
            return new Film(resultSet.getString("name"),
                    new ObjectMapper().readValue(
                            resultSet.getString("description"),
                            Film.Description.class),
                    resultSet.getLong("id"));
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
    public Optional<List<Film>> findAllFilms(Film.Description description) throws JsonProcessingException {
        return Optional.of(jdbcUtil.findAll(Q_FIND_ALL_FILMS_BY_DESCRIPTION, filmRowMapper,
                new ObjectMapper()
                        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                        .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
                        .writerWithDefaultPrettyPrinter().writeValueAsString(description)));
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

    @Override
    public Optional<Film> findFilmById(Long filmId) {
        return Optional.of(jdbcUtil.findOne(Q_FIND_BY_ID, filmRowMapper, filmId));
    }
}
