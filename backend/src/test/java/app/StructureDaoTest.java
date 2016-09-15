package app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@IntegrationTest
@ActiveProfiles("test")
public class StructureDaoTest {

    @Autowired
    private StructureDao structureDao;

    @Test
    @Transactional
    @Rollback(true)
    public void save() {

        NodeEntity nodeA = NodeEntity.builder().id(28).build();
        NodeEntity nodeB = NodeEntity.builder().id(83).build();
        List<NodeEntity> nodes = Arrays.asList(nodeA, nodeB);

        ForceMomentEntity forceA = ForceMomentEntity.builder().elementId(43).fx(234d).build();
        ForceMomentEntity forceB = ForceMomentEntity.builder().elementId(43).fx(3097d).build();
        List<ForceMomentEntity> forces = Arrays.asList(forceA, forceB);

        ElementEntity elementB = ElementEntity.builder().id(62).build();
        ElementEntity elementA = ElementEntity.builder().id(43).forceMoments(forces).build();
        List<ElementEntity> elements = Arrays.asList(elementA, elementB);

        StructureEntity structure = StructureEntity.builder()
                .id("tall-building")
                .postDate(6345696785L)
                .mass(343)
                .elements(elements)
                .nodes(nodes)
                .build();

        structureDao.save(structure);

        StructureEntity returnedStructure = structureDao.findById("tall-building");

        assertThat(returnedStructure.getElements().size(), is(equalTo(2)));
        assertThat(returnedStructure.getNodes().size(), is(equalTo(2)));
        assertThat(returnedStructure.getElements().get(0).getForceMoments().size(), is(equalTo(2)));
    }
}