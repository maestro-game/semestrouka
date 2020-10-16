package servlets_jdbc.repositories;

import servlets_jdbc.models.Person;
import servlets_jdbc.models.rowmappers.RowMapper;
import servlets_jdbc.services.security.models.Role;

import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private final String Q_SELECT_ALL = "SELECT * FROM persons;";
    private final String Q_SELECT_ONE_BY_USERNAME = "SELECT * FROM persons WHERE username = ? ;";
    //    language=sql
    private final String Q_SAVE = "INSERT INTO persons (username, password, email, name, surname, phone, role) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

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
}
