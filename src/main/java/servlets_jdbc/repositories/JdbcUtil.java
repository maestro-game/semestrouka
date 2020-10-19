package servlets_jdbc.repositories;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlets_jdbc.models.rowmappers.RowMapper;
import servlets_jdbc.services.security.models.Role;

import java.io.Closeable;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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

    <T> List<T> findAll(final String sql, RowMapper<T> rowMapper) {

        List<T> res = new LinkedList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            try (ResultSet resultSet = stmt.executeQuery(sql)) {

                while (resultSet.next()) {
                    res.add(rowMapper.mapRow(resultSet));
                }

            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException(e);
        }

        return res;
    }

    <T> List<T> findAll(final String sql, RowMapper<T> rowMapper, Object... args) {

        List<T> res = new LinkedList<>();

        try (Connection conn = connect();
             PreparedStatement stmt = wrapObjects(conn.prepareStatement(sql), args)) {

            try (ResultSet resultSet = stmt.executeQuery()) {

                while (resultSet.next()) {
                    res.add(rowMapper.mapRow(resultSet));
                }

            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

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
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

        return res;

    }

    private PreparedStatement wrapObjects(PreparedStatement stmt, Object... args) throws SQLException {
        int k = 1;
        for (Object arg : args) {
//            Custom objects ( that's not checking in PreparedStatement.setObject() )
            if (arg instanceof Role) {
                arg = ((Role) arg).name();
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
