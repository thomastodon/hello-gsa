package app;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@IntegrationTest
@ActiveProfiles("test")
public class StructureDaoTest {

    @Autowired
    private StructureDao structureDao;
    private StructureEntity structure;

    @Before
    public void setup() {
        structure = StructureEntity.builder()
                .id("tall-building")
                .postDate(6345696785L)
                .mass(343)
                .build();

        NodeEntity nodeA = NodeEntity.builder()
                .id(28)
                .x(3421d)
                .y(13412d)
                .z(123d)
                .build();
        NodeEntity nodeB = NodeEntity.builder()
                .id(32)
                .x(826d)
                .y(874d)
                .z(83576d)
                .build();
        NodeEntity nodeC = NodeEntity.builder()
                .id(75)
                .x(2398d)
                .y(4252d)
                .z(8606d)
                .build();
        NodeEntity nodeD = NodeEntity.builder()
                .id(83)
                .x(9864d)
                .y(4523d)
                .z(253d)
                .build();
        List<NodeEntity> nodes = asList(nodeA, nodeB, nodeC, nodeD);

        ForceEntity forceA = ForceEntity.builder()
                .elementId(93)
                .structureId("tall-building")
                .position(0)
                .resultCaseId(65)
                .fx(234d)
                .fy(8675d)
                .fz(2845d)
                .build();
        ForceEntity forceB = ForceEntity.builder()
                .elementId(93)
                .structureId("tall-building")
                .position(1)
                .resultCaseId(65)
                .fx(2453d)
                .fy(5628d)
                .fz(0849d)
                .build();
        List<ForceEntity> forces = asList(forceA, forceB);

        ElementEntity elementB = ElementEntity.builder()
                .id(62)
                .node1(nodeA)
                .node2(nodeB)
                .groupId(40)
                .sectionPropertyId(101)
                .type("BAR")
                .forces(emptyList())
                .build();
        ElementEntity elementA = ElementEntity.builder()
                .id(93)
                .node1(nodeC)
                .node2(nodeD)
                .groupId(30)
                .sectionPropertyId(101)
                .type("BEAM")
                .forces(forces)
                .build();
        List<ElementEntity> elements = asList(elementA, elementB);

        structure.setNodes(nodes);
        structure.setElements(elements);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void save_correctNumberOfEntitiesArePersisted() {
        structureDao.save(structure);

        StructureEntity returnedStructure = structureDao.findById("tall-building");

        assertThat(returnedStructure).isEqualToComparingFieldByFieldRecursively(structure);
    }
}