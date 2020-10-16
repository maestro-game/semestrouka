package servlets_jdbc.repositories;

import org.junit.jupiter.api.*;
import servlets_jdbc.models.rowmappers.RowMapper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
final class JdbcUtilTest extends JdbcUtil {

    private final String table;

    private final String intColumn;

    private final String textColumn;

    private final String Q_INSERT;

    private final String Q_DELETE;

    private final String Q_FIND_ONE;

    private final String Q_FIND_ALL;

    private final String Q_FIND_ALL_BY_INT_COLUMN;

    {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("test.properties"));
            table = properties.getProperty("test.table");

            intColumn = properties.getProperty("test.col2");
            textColumn = properties.getProperty("test.col3");

            Q_INSERT = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?);",
                    table, intColumn, textColumn);

            Q_DELETE = String.format("DELETE FROM %s ;", table);

            Q_FIND_ONE = String.format("SELECT * FROM %s WHERE %s = ? LIMIT 1 ;", table, intColumn);

            Q_FIND_ALL = String.format("SELECT * FROM %s ;", table);

            Q_FIND_ALL_BY_INT_COLUMN = String.format("SELECT * FROM %s WHERE %s = ? ;", table, intColumn);
        } catch (IOException e) {
            throw new IllegalStateException("Properties class for testing is missing");
        }
    }

    private static class TestValue {
        private int intColumn;
        private String textColumn;

        public TestValue(int intColumn, String textColumn) {
            this.intColumn = intColumn;
            this.textColumn = textColumn;
        }

    }

    RowMapper<TestValue> testRowMapper = resultSet ->
            new TestValue(resultSet.getInt(intColumn), resultSet.getString(textColumn));

    static JdbcUtil jdbcUtil;

    private JdbcUtilTest() {}

    @BeforeAll
    static void setupWrong() {
        assertThrows(IllegalArgumentException.class, () ->
                jdbcUtil = new JdbcUtil("datasouse.properties"));
    }

    @BeforeAll
    static void setupDefault() {
        jdbcUtil = new JdbcUtil();
    }

    @Test
    void checkConnection() {
        try (Connection conn = jdbcUtil.connect()) {
            assertTrue(conn.isValid(1));
        } catch (SQLException e) {
            throw new IllegalStateException("Can't establish connection", e);
        }
    }

    @Test
    @Order(1)
    void update() {
        assertEquals(jdbcUtil.update(Q_INSERT, 42, "Hello"), 1);
    }

    @Test
    void findAll() {
        assertFalse(jdbcUtil.findAll(Q_FIND_ALL, testRowMapper).isEmpty());
        assertFalse(jdbcUtil.findAll(Q_FIND_ALL_BY_INT_COLUMN, testRowMapper, 42).isEmpty());
    }

    @Test
    void findOne() {
        assertNotNull(jdbcUtil.findOne(Q_FIND_ONE, testRowMapper, 42));
    }

    @Test
    @Order(Integer.MAX_VALUE)
    void delete() {
        assertNotEquals(jdbcUtil.delete(Q_DELETE), 0);
    }
}
