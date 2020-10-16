package servlets_jdbc.repositories;

import servlets_jdbc.models.CookieValue;
import servlets_jdbc.models.rowmappers.RowMapper;

public class CookieRepositoryImpl implements CookieRepository {
    private final String Q_SELECT_ALL = "SELECT * FROM cookies;";
    private final String Q_SELECT_ONE_BY_VALUE = "SELECT * FROM cookies WHERE value = ? ;";
    private final String Q_SAVE = "INSERT INTO cookies (value, username) VALUES (?, ?)";

    private final JdbcUtil jdbcUtil;

    private final RowMapper<CookieValue> rowMapper =
            resultSet -> CookieValue
                    .builder()
                    .user(resultSet.getString("username"))
                    .value(resultSet.getString("value"))
                    .build();

    public CookieRepositoryImpl(JdbcUtil jdbcUtil) {
        this.jdbcUtil = jdbcUtil;
    }

    @Override
    public int save(CookieValue cookieValue) {
        return jdbcUtil.update(Q_SAVE, cookieValue.getValue(), cookieValue.getUser());
    }

    @Override
    public String findUsernameByValue(String value) throws NullPointerException {
        return jdbcUtil.findOne(Q_SELECT_ONE_BY_VALUE, rowMapper, value).getUser();
    }
}
