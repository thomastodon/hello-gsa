package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class ElementDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public ElementEntity findById(String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM element " +
                        "JOIN node node_1 on node_1.id = element.node_1_id " +
                        "JOIN node node_2 on node_2.id = element.node_2_id " +
                        "WHERE element.id = ?;",
                new ElementRowMapper(),
                id);
    }

    private class ElementRowMapper implements RowMapper<ElementEntity> {
        @Override
        public ElementEntity mapRow(ResultSet resultSet, int rowNum)
                throws SQLException {
            ElementEntity element = new ElementEntity();
            element.setId(resultSet.getInt("element.id"));

            // TODO: use builders for all of the row mappers

            return element;
        }
    }
}

