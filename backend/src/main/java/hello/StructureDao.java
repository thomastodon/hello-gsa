package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Repository
public class StructureDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // TODO: write some tests for coincident elements
    // TODO: handle exception deliberately
    @Transactional
    void save(StructureEntity structureEntity) {
        jdbcTemplate.update(
                "INSERT INTO structure (id, post_date, mass)" +
                        "VALUES (?, ?, ?);",
                structureEntity.getId(),
                structureEntity.getPostDate(),
                structureEntity.getMass()
        );

        List<NodeEntity> nodes = structureEntity.getNodes();
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
                NodeEntity node = nodes.get(i);
                ps.setString(1, structureEntity.getId());
                ps.setInt(2, node.getId());
                ps.setDouble(3, node.getX());
                ps.setDouble(4, node.getY());
                ps.setDouble(5, node.getZ());
            }

            @Override
            public int getBatchSize() {
                return nodes.size();
            }
        });

        List<ElementEntity> elements = structureEntity.getElements();
        sql = "INSERT INTO element (" +
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
                ElementEntity element = elements.get(i);
                ps.setString(1, structureEntity.getId());
                ps.setInt(2, element.getId());
                ps.setInt(3, element.getNode1Id());
                ps.setInt(4, element.getNode2Id());
                ps.setInt(5, element.getSectionPropertyId());
                ps.setString(6, element.getType());
                ps.setInt(7, element.getGroupId());
            }

            @Override
            public int getBatchSize() {
                return elements.size();
            }
        });

        List<ForceMomentEntity> forceMoments =
                elements.stream()
                        .flatMap(l -> l.getForceMoments().stream())
                        .collect(Collectors.toList());
        sql = "INSERT INTO force_moment (" +
                "structure_id, " +
                "element_id, " +
                "result_case_id, " +
                "position, " +
                "fx, " +
                "fy, " +
                "fz" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ForceMomentEntity forceMoment = forceMoments.get(i);
                ps.setString(1, forceMoment.getStructureId());
                ps.setInt(2, forceMoment.getElementId());
                ps.setInt(3, forceMoment.getResultCaseId());
                ps.setInt(4, forceMoment.getPosition());
                ps.setDouble(5, forceMoment.getFx());
                ps.setDouble(6, forceMoment.getFy());
                ps.setDouble(7, forceMoment.getFz());
            }

            @Override
            public int getBatchSize() {
                return forceMoments.size();
            }
        });
    }

    @Transactional(readOnly = true)
    public StructureEntity findById(String id) {

        String sql =
                "SELECT * FROM structure " +
                "JOIN element " +
                "   ON element.structure_id = structure.id " +
                "JOIN node node_1 " +
                "   ON node_1.id = element.node_1_id " +
                "   AND node_1.structure_id = structure.id " +
                "JOIN node node_2 " +
                "   ON node_2.id = element.node_2_id " +
                "   AND node_2.structure_id = structure.id " +
                "JOIN force_moment " +
                "   ON force_moment.element_id = element.id " +
                "   AND force_moment.structure_id = structure.id " +
                "WHERE structure.id = ?";

        return jdbcTemplate.query(sql, new StructureResultSetExtractor(), id);
    }

    private class StructureResultSetExtractor implements ResultSetExtractor<StructureEntity> {

        public StructureEntity extractData(ResultSet resultSet) throws SQLException, DataAccessException {

            Map<Integer, ElementEntity> elementMap = new HashMap<Integer, ElementEntity>();
            Map<Integer, NodeEntity> nodeMap = new HashMap<Integer, NodeEntity>();
            StructureEntity structure = new StructureEntity();

            while (resultSet.next()) {

                if (resultSet.getRow() == 1) {
                    structure = StructureEntity.builder()
                            .id(resultSet.getString("structure.id"))
                            .postDate(resultSet.getLong("structure.post_date"))
                            .mass(resultSet.getInt("structure.mass"))
                            .build();
                }

                // TODO: collapse node1 and node2 into a list of nodes
                Integer node1Id = resultSet.getInt("node_1.id");
                NodeEntity node1 = nodeMap.get(node1Id);
                if (node1 == null) {
                    node1 = NodeEntity.builder()
                            .id(node1Id)
                            .x(resultSet.getDouble("node_1.x"))
                            .y(resultSet.getDouble("node_1.y"))
                            .z(resultSet.getDouble("node_1.z"))
                            .build();
                    nodeMap.put(node1Id, node1);
                }

                Integer node2Id = resultSet.getInt("node_1.id");
                NodeEntity node2 = nodeMap.get(node2Id);
                if (node2 == null) {
                    node2 = NodeEntity.builder()
                            .id(node2Id)
                            .x(resultSet.getDouble("node_2.x"))
                            .y(resultSet.getDouble("node_2.y"))
                            .z(resultSet.getDouble("node_2.z"))
                            .build();
                    nodeMap.put(node2Id, node2);
                }

                Integer elementId = resultSet.getInt("element.id");
                ElementEntity element = elementMap.get(elementId);
                if (element == null) {
                    element = ElementEntity.builder()
                            .id(elementId)
                            .node1(node1)
                            .node2(node2)
                            .sectionPropertyId(resultSet.getInt("element.section_property_id"))
                            .groupId(resultSet.getInt("element.group_id"))
                            .type(resultSet.getString("element.type"))
                            .forceMoments(new ArrayList<>())
                            .build();
                    elementMap.put(elementId, element);
                }

                ForceMomentEntity forceMoment = ForceMomentEntity.builder()
                        .resultCaseId(resultSet.getInt("force_moment.result_case_id"))
                        .position(resultSet.getInt("force_moment.position"))
                        .fx(resultSet.getDouble("force_moment.fx"))
                        .fy(resultSet.getDouble("force_moment.fy"))
                        .fz(resultSet.getDouble("force_moment.fz"))
                        .build();
                elementMap.get(elementId).getForceMoments().add(forceMoment);
                // TODO: include forces and moments
            }
            structure.setElements(new ArrayList<>(elementMap.values()));
            return structure;
        }
    }

}

// TODO: delete structure endpoint