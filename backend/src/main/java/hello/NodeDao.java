package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class NodeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public NodeEntity findById(String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM node " +
                        "WHERE node.id = ?;",
                new NodeRowMapper(),
                id);
    }

    private class NodeRowMapper implements RowMapper<NodeEntity> {
        @Override
        public NodeEntity mapRow(ResultSet resultSet, int rowNum)
                throws SQLException {
            NodeEntity node = new NodeEntity();
            node.setId(resultSet.getInt("node.id"));

            // TODO: use builders for all of the row mappers

            return node;
        }
    }
}

