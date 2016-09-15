package app;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceTest {

    private ApplicationService subject;
    private String input;
    @Mock private StructureDao structureDao;

    @Before
    public void setup() {
        subject = new ApplicationService(structureDao);

        input = "NODE,3327,,NO_RGB,16.6620,1.50000,22.3340\n" +
                "NODE,3326,,NO_RGB,13.8710,1.50000,21.6450\n" +
                "NODE,3325,,NO_RGB,55.2660,37.0000,17.7580\n" +
                "NODE,3324,,NO_RGB,0.000000,37.0000,3.09800\n" +
                "NODE,3323,,NO_RGB,0.000000,37.0000,6.19600\n" +
                "NODE,3322,,NO_RGB,0.150017,37.0000,9.28700\n" +
                "EL,2430,,NO_RGB,BEAM,211,40,3327,3326\n" +
                "EL,2431,,NO_RGB,BEAM,207,40,3326,3325\n" +
                "EL,2432,,NO_RGB,BEAM,213,40,3325,3324\n" +
                "EL,2433,,NO_RGB,BEAM,221,40,3324,3323\n" +
                "EL,2434,,NO_RGB,BEAM,225,40,3323,3322\n" +
                "FORCE,2430,1,0,23965.4,-30.5844,109.418\n" +
                "FORCE,2430,1,1,-23396.0,30.5844,-109.418\n" +
                "FORCE,2431,1,0,12684.0,-52.6026,95.5117\n" +
                "FORCE,2431,1,1,-12311.1,52.6026,-95.5117\n" +
                "FORCE,2432,1,0,1496.18,35.8456,-112.442\n" +
                "FORCE,2432,1,1,-928.105,-35.8456,140.008\n" +
                "FORCE,2433,1,0,-7067.06,-27.0792,3.11592\n" +
                "FORCE,2433,1,1,8050.41,27.0792,216.097\n" +
                "FORCE,2434,1,0,-10867.8,-56.8867,242.496\n" +
                "FORCE,2434,1,1,12107.0,56.8867,276.379\n" +
                "MOMENT,2430,1,0,-240.370,-121.529,21.0900";
    }

    @Test
    public void postStructure_delegatesToDao() {
        String structureId = "tall-building";

        StructureEntity structureEntity = subject.postStructure(structureId, input);

        verify(structureDao).save(any(StructureEntity.class));
    }

    @Test
    public void parseStructureCsv_yieldsTheCorrectNumberOfForces() {
        String structureId = "tall-building";

        StructureEntity structureEntity = subject.parseStructureCsv(structureId, input);

        long forceMomentEntityCount = structureEntity.getElements()
                .stream()
                .flatMap(l -> l.getForceMoments().stream())
                .count();
        assertThat(forceMomentEntityCount, is(equalTo(10L)));
    }

    @Test
    public void parseStructureCsv_yieldsTheCorrectNumberOfElements() {
        String structureId = "tall-building";

        StructureEntity structureEntity = subject.parseStructureCsv(structureId, input);

        long elementEntityCount = structureEntity.getElements().size();
        assertThat(elementEntityCount, is(equalTo(5L)));
    }

    @Test
    public void parseStructureCsv_yieldsTheCorrectNumberOfNodes() {
        String structureId = "tall-building";

        StructureEntity structureEntity = subject.parseStructureCsv(structureId, input);

        long nodeEntityCount = structureEntity.getNodes().size();
        assertThat(nodeEntityCount, is(equalTo(6L)));
    }

    @Test
    public void getStructure_callsToDao() {
        subject.getStructure("tall building");

        verify(structureDao).findById(anyString());
    }
}