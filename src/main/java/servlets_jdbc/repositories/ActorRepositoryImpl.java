package servlets_jdbc.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import servlets_jdbc.models.Actor;
import servlets_jdbc.models.rowmappers.RowMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ActorRepositoryImpl implements ActorRepository {
    private final JdbcUtil jdbcUtil;

    //  language=SQL
    private final static String Q_FIND_ACTOR_BY_ID = "SELECT * FROM actors WHERE id = ? ;";
    //  language=SQL
    private static final String Q_FIND_ALL_ACTORS = "SELECT * FROM actors;";
    //  language=SQL
    private static final String Q_SAVE_ACTOR = "INSERT INTO actors (fullname, description) " +
            "VALUES (?, ?::jsonb);";

    private final RowMapper<Actor> actorRowMapper = resultSet ->
            Actor.builder()
                    .id(resultSet.getLong("id"))
                    .fullname(resultSet.getString("fullname"))
                    .description(
                            new ObjectMapper()
                                    .readValue(
                                            resultSet.getString("description"),
                                            Actor.Description.class)
                    )
                    .build();

    public ActorRepositoryImpl(JdbcUtil jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    @Override
    public Optional<Actor> findActorById(Long actorId) {
        return Optional.of(jdbcUtil.findOne(Q_FIND_ACTOR_BY_ID, actorRowMapper, actorId));
    }

    @Override
    public Optional<List<Actor>> findAllActors() {
        return Optional.of(jdbcUtil.findAll(Q_FIND_ALL_ACTORS, actorRowMapper));
    }

    @Override
    public Optional<Actor> saveActor(Actor actor) {
        Long id = jdbcUtil.save(Q_SAVE_ACTOR, (stmt) -> {
            try {
                stmt.setString(1, actor.getFullname());
                stmt.setString(2, new ObjectMapper().writeValueAsString(actor.getDescription()));
            } catch (SQLException | JsonProcessingException e) {
                throw new IllegalArgumentException(e);
            }
            return stmt;
        });
        actor.setId(id);
        return Optional.of(actor);
    }
}
