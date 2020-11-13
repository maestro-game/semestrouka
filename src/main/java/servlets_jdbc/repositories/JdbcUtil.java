package servlets_jdbc.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlets_jdbc.models.reviews.Mark;
import servlets_jdbc.models.rowmappers.RowMapper;

import java.io.Closeable;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class JdbcUtil implements Closeable {

    private final HikariDataSource hikariDataSource;

    private final Logger logger = LoggerFactory.getLogger(JdbcUtil.class);

    public JdbcUtil() {
        hikariDataSource = new HikariDataSource(hikariConfigProperLoader("datasource.properties"));
    }

    public JdbcUtil(String properties) {
        hikariDataSource = new HikariDataSource(hikariConfigProperLoader(properties));
    }

    private HikariConfig hikariConfigProperLoader(String properties) {
        try {
            return new HikariConfig(Paths.get(
                    Objects.requireNonNull(getClass().getClassLoader().getResource(properties)).toURI()).toString());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Can't find or access properties file " + properties, e);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("URI is not formatted properly", e);
        }
    }

    Long save(final String sql, Function<PreparedStatement, PreparedStatement> customWrapper) {
        int rows;
        Long res;

        try (Connection conn = connect();
             PreparedStatement stmt = customWrapper.apply(conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))) {

            rows = stmt.executeUpdate();

            if (rows != 1) {
                throw new IllegalArgumentException("Can't save");
            }

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                res = (keys.next()) ? keys.getLong("id") : null;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new IllegalStateException(e);
        }

        return res;
    }

    protected Connection connect() {
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            throw new IllegalStateException("Can't establish connection", e);
        }
    }

    int update(final String sql, Object... args) {
        int res;

        try (Connection conn = connect();
             PreparedStatement stmt = wrapObjects(conn.prepareStatement(sql), args)) {

            res = stmt.executeUpdate();

            if (res != 1) {
                throw new IllegalArgumentException();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new IllegalStateException(e);
        }
        return res;
    }

    int delete(final String sql) {
        int res;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            res = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new IllegalStateException(e);
        }

        return res;
    }


    void delete(final String sql, Object... args) {
        try (Connection conn = connect();
             PreparedStatement stmt = wrapObjects(conn.prepareStatement(sql), args)) {

            logger.info(String.valueOf(stmt.executeUpdate()));
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    <T> List<T> findAll(final String sql, RowMapper<T> rowMapper) {

        List<T> res = new LinkedList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            try (ResultSet resultSet = stmt.executeQuery(sql)) {

                while (resultSet.next()) {
                    res.add(rowMapper.mapRow(resultSet));
                }

            }
        } catch (SQLException | JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException(e);
        }

        return res;
    }

    <T> List<T> findAll(final String sql, RowMapper<T> rowMapper, Object... args) {

        List<T> res = new LinkedList<>();

        try (Connection conn = connect();
             PreparedStatement stmt = wrapObjects(conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS), args)) {

            try (ResultSet resultSet = stmt.executeQuery()) {

                while (resultSet.next()) {
                    res.add(rowMapper.mapRow(resultSet));
                }

            }
        } catch (SQLException | JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
        logger.info(Arrays.toString(res.toArray()));
        return res;
    }

    <T> T findOne(final String sql, RowMapper<T> rowMapper, Object... args) {

        T res = null;

        try (Connection conn = connect();
             PreparedStatement stmt = wrapObjects(conn.prepareStatement(sql), args)) {

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    res = rowMapper.mapRow(resultSet);
                }
            }
        } catch (SQLException | JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }

        return res;

    }


    String findOne(final String sql, Object... args) {

        String res = null;

        try (Connection conn = connect();
             PreparedStatement stmt = wrapObjects(conn.prepareStatement(sql), args)) {

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    res = resultSet.getString(1);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

        return res;

    }

    private PreparedStatement wrapObjects(PreparedStatement stmt, Object... args) throws SQLException {
        int k = 1;
        for (Object arg : args) {
//            Custom objects ( which is not being checked in PreparedStatement.setObject() )
            if (arg instanceof Mark) {
                arg = ((Mark) arg).toInt();
                stmt.setObject(k++, arg);
                continue;
            } else if (arg instanceof Enum) {
                arg = ((Enum) arg).name();
                stmt.setObject(k++, arg);
                continue;
            }
            stmt.setObject(k++, arg);
        }
        return stmt;
    }

    @Override
    public void close() throws IOException {
        if (hikariDataSource.isRunning()) {
            hikariDataSource.close();
        }
    }
}
