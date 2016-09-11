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
public class ForceMomentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

//    @Transactional(readOnly = true)
//    public ForceMomentEntity findById(String id) {
//        return jdbcTemplate.queryForObject("SELECT * FROM force_moment " +
//                        "JOIN node node_1 on node_1.id = force_moment.node_1_id " +
//                        "WHERE force_moment.id = ?;",
//                new ForceMomentRowMapper(),
//                id);
//    }

    public void save(Set<ForceMomentEntity> forceMoments){

        List<ForceMomentEntity> forceMomentList = new ArrayList<ForceMomentEntity>(forceMoments);
        String sql = "INSERT INTO force_moment (" +
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
                ForceMomentEntity forceMoment = forceMomentList.get(i);

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
                return forceMomentList.size();
            }
        });
    }


    private class ForceMomentRowMapper implements RowMapper<ForceMomentEntity> {
        @Override
        public ForceMomentEntity mapRow(ResultSet resultSet, int rowNum)
                throws SQLException {
            ForceMomentEntity forceMoment = new ForceMomentEntity();

            // TODO: use builders for all of the row mappers

            return forceMoment;
        }
    }
}

