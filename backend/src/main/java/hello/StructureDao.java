package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Repository
public class StructureDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public Structure findById(String id) {
        return jdbcTemplate.query("SELECT * FROM structure " +
                        "JOIN element on element.structure_id = structure.id " +
                        "JOIN node node_1 on node_1.id = element.node_1_id " +
                        "JOIN node node_2 on node_2.id = element.node_2_id " +
                        "WHERE structure.id = ?",
                new StructureResultSetExtractor(),
                id);
    }

    @Transactional
    void save(StructureEntity structureEntity) {
        jdbcTemplate.update(
                "INSERT INTO structure (id, post_date, mass)" +
                        "VALUES (?, ?, ?);",
                structureEntity.getId(),
                structureEntity.getPostDate(),
                structureEntity.getMass());
    }

    private class StructureResultSetExtractor implements ResultSetExtractor<Structure> {

        public Structure extractData(ResultSet resultSet) throws SQLException, DataAccessException {

            Map<String, Structure> structureMap = new HashMap<String, Structure>();
            Map<Integer, Element> elementMap = new HashMap<Integer, Element>();
            Map<Integer, Node> nodeMap = new HashMap<Integer, Node>();
            Structure structure = new Structure();

            while (resultSet.next()) {
                String structureId = resultSet.getString("structure.id");
                structure = structureMap.get(structureId);
                if (structure == null) {
                    structure = new Structure();
                    structure.setId(structureId);
                    structure.setPostDate(resultSet.getLong("structure.post_date"));
                    structure.setMass(resultSet.getInt("structure.mass"));
                    structureMap.put(structureId, structure);
                }

                // TODO: collapse node1 and node2 into a list of nodes

                Integer node1Id = resultSet.getInt("node_1.id");
                Node node1 = nodeMap.get(node1Id);
                if (node1 == null) {
                    node1 = Node.builder()
                            .id(resultSet.getInt("node_1.id"))
                            .x(resultSet.getDouble("node_1.x"))
                            .y(resultSet.getDouble("node_1.y"))
                            .z(resultSet.getDouble("node_1.z"))
                            .build();
                    nodeMap.put(node1Id, node1);
                }

                Integer node2Id = resultSet.getInt("node_1.id");
                Node node2 = nodeMap.get(node2Id);
                if (node2 == null) {
                    node2 = Node.builder()
                            .id(resultSet.getInt("node_2.id"))
                            .x(resultSet.getDouble("node_2.x"))
                            .y(resultSet.getDouble("node_2.y"))
                            .z(resultSet.getDouble("node_2.z"))
                            .build();
                    nodeMap.put(node2Id, node2);
                }

                Integer elementId = resultSet.getInt("element.id");
                Element element = elementMap.get(elementId);
                if (element == null) {
                    element = Element.builder()
                            .id(resultSet.getInt("element.id"))
                            .node1(node1)
                            .node2(node2)
                            .sectionPropertyId(resultSet.getInt("element.section_property_id"))
                            .groupId(resultSet.getInt("element.group_id"))
                            .type(resultSet.getString("element.type"))
                            .build();
                    elementMap.put(elementId, element);
                }
            }
            structure.setElements(new HashSet<Element>(elementMap.values()));
            return structure;
        }
    }
}
