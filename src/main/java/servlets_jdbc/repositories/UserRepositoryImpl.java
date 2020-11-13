package servlets_jdbc.repositories;

import servlets_jdbc.models.Person;
import servlets_jdbc.models.forms.ProfileForm;
import servlets_jdbc.models.rowmappers.RowMapper;
import servlets_jdbc.services.security.models.Role;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserRepositoryImpl implements UserRepository {
    //    language=sql
    private static final String Q_UPDATE_GENRES =
            "UPDATE persons SET interests = (CASE " +
                    "WHEN interests IS NULL " +
                    "   THEN '{\"genres\":[' || ? || ']}'::jsonb" +
                    "   ELSE jsonb_set(interests, array['genres'], (interests->'genres')::jsonb || ('[' || ? || ']')::jsonb, true) " +
                    "END) WHERE username = ? ;";
    private static final String Q_INSERT_INFO = "UPDATE persons SET name = ? AND surname = ? AND phone = ? WHERE username = ? ;";
    private static final String Q_SELECT_ALL = "SELECT * FROM persons;";
    private static final String Q_SELECT_ONE_BY_USERNAME = "SELECT * FROM persons WHERE username = ? ;";
    private static final String Q_SAVE = "INSERT INTO persons (username, password, email, name, surname, phone, role) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String Q_FIND_GENRES_BY_USERNAME = "SELECT interests->'genres' as genres FROM persons WHERE username = ? ;";
    private static final String Q_DELETE_GENRES =
            "UPDATE persons SET interests = " +
                    "jsonb_set(interests, '{genres}', (interests->'genres') - ?) WHERE username = ? ;";
    private final JdbcUtil jdbcUtil;

    private final RowMapper<Person> rowMapper =
            resultSet -> Person
                    .builder()
                    .username(resultSet.getString("username"))
                    .password(resultSet.getString("password"))
                    .email(resultSet.getString("email"))
                    .name(resultSet.getString("name"))
                    .surname(resultSet.getString("surname"))
                    .phone(resultSet.getString("phone"))
                    .role(Enum.valueOf(Role.class, resultSet.getString("role")))
                    .build();

    public UserRepositoryImpl(JdbcUtil jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    @Override
    public int save(Person person) {
        return jdbcUtil.update(Q_SAVE,
                person.getUsername(), person.getPassword(), person.getEmail(),
                person.getName(), person.getSurname(), person.getPhone(),
                person.getRole());
    }

    @Override
    public Optional<Person> findOneByUsername(String username) {
        return Optional.ofNullable(jdbcUtil.findOne(Q_SELECT_ONE_BY_USERNAME, rowMapper, username));
    }

    @Override
    public List<Person> findAll() {
        return jdbcUtil.findAll(Q_SELECT_ALL, rowMapper);
    }

    @Override
    public ProfileForm updateInfo(ProfileForm userInfo, boolean toDelete) {
        if (userInfo.containGenres()) {
            updateGenres(userInfo, toDelete);
        }
        jdbcUtil.save(Q_INSERT_INFO,
                stmt -> {
                    try {
                        stmt.setString(1, userInfo.getName());
                        stmt.setString(2, userInfo.getSurname());
                        stmt.setString(3, userInfo.getPhone());
                    } catch (SQLException e) {
                        throw new IllegalArgumentException(e);
                    }
                    return stmt;
                }
        );
        return userInfo;
    }

    @Override
    public Optional<List<String>> getGenresByUsername(String username) {
        return Optional.of(List.of(jdbcUtil.findOne(Q_FIND_GENRES_BY_USERNAME, username).replace("[", "").replace("]", "").replaceAll("\"", "").split(", ")));
    }

    private void updateGenres(ProfileForm userInfo, Boolean toDelete) {
        String[] genresArray = (String[]) userInfo.getGenres().toArray();
        if (toDelete) {
            for (String genre : genresArray) {
                jdbcUtil.update(Q_DELETE_GENRES, genre, userInfo.getUsername());
            }
        } else {
            String genres = genresArray.length > 1
                    ? quoteWrap(genresArray).collect(Collectors.joining(", "))
                    : genresArray[0];
            jdbcUtil.update(Q_UPDATE_GENRES, genres, genres, userInfo.getUsername());
        }

    }

    private Stream<String> quoteWrap(String... string) {
        return Arrays.stream(string).map((s) -> "\"" + s + "\"");
    }
}
