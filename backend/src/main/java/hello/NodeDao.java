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
    public Node findById(String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM node " +
                        "WHERE node.id = ?;",
                new NodeRowMapper(),
                id);
    }

    @Transactional
    void save(NodeEntity nodeEntity) {
        jdbcTemplate.update(
                "INSERT INTO node (" +
                        "structure_id, " +
                        "id, " +
                        "x, " +
                        "y, " +
                        "z, " +
                        ") VALUES (?, ?, ?, ?, ?);",
                nodeEntity.getStructureId(),
                nodeEntity.getId(),
                nodeEntity.getX(),
                nodeEntity.getY(),
                nodeEntity.getZ()
        );
    }

    public void save(Set<NodeEntity> nodes){

        List<NodeEntity> nodeList = new ArrayList<NodeEntity>(nodes);
        String sql = "INSERT INTO node (" +
                        "structure_id, " +
                        "id, " +
                        "x, " +
                        "y, " +
                        "z " +
                        ") VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                NodeEntity node = nodeList.get(i);

                ps.setString(1, node.getStructureId());
                ps.setInt(2, node.getId());
                ps.setDouble(3, node.getX());
                ps.setDouble(4, node.getY());
                ps.setDouble(5, node.getZ());
            }

            @Override
            public int getBatchSize() {
                return nodeList.size();
            }
        });
    }

    private class NodeRowMapper implements RowMapper<Node> {
        @Override
        public Node mapRow(ResultSet resultSet, int rowNum)
                throws SQLException {
            Node node = new Node();
            node.setId(resultSet.getInt("node.id"));

            // TODO: use builders for all of the row mappers

            return node;
        }
    }
}

