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
    public Element findById(String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM element " +
                        "JOIN node node_1 on node_1.id = element.node_1_id " +
                        "JOIN node node_2 on node_2.id = element.node_2_id " +
                        "WHERE element.id = ?;",
                new ElementRowMapper(),
                id);
    }

    public void save(Set<ElementEntity> elements){

        List<ElementEntity> elementList = new ArrayList<ElementEntity>(elements);
        String sql = "INSERT INTO element (" +
                "structure_id, " +
                "id, " +
                "node_1_id, " +
                "node_2_id, " +
                "section_property_id, " +
                "type, " +
                "group_id" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?);";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ElementEntity element = elementList.get(i);

                ps.setString(1, element.getStructureId());
                ps.setInt(2, element.getId());
                ps.setInt(3, element.getNode1Id());
                ps.setInt(4, element.getNode2Id());
                ps.setInt(5, element.getSectionPropertyId());
                ps.setString(6, element.getType());
                ps.setInt(7, element.getGroupId());
            }

            @Override
            public int getBatchSize() {
                return elementList.size();
            }
        });
    }


    @Transactional
    void save(ElementEntity elementEntity) {
        jdbcTemplate.update(
                "INSERT INTO element (" +
                        "structure_id, " +
                        "id, " +
                        "node_1_id, " +
                        "node_2_id, " +
                        "section_property_id, " +
                        "type, " +
                        "group_id" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?);",
                elementEntity.getStructureId(),
                elementEntity.getId(),
                elementEntity.getNode1Id(),
                elementEntity.getNode2Id(),
                elementEntity.getSectionPropertyId(),
                elementEntity.getType(),
                elementEntity.getGroupId()
        );
    }

    private class ElementRowMapper implements RowMapper<Element> {
        @Override
        public Element mapRow(ResultSet resultSet, int rowNum)
                throws SQLException {
            Element element = new Element();
            element.setId(resultSet.getInt("element.id"));

            // TODO: use builders for all of the row mappers

            return element;
        }
    }
}

