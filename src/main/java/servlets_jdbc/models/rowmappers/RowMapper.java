package servlets_jdbc.models.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RowMapper<T> {
    T mapRow(ResultSet resultSet) throws SQLException;
}
