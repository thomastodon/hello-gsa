package hello;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTranslatorTest {

    private ApplicationTranslator subject;
    @Mock ElementRepository mockElementRepository;
    @Mock NodeRepository mockNodeRepository;

    @Before
    public void setup() {
        subject = new ApplicationTranslator(
                mockNodeRepository,
                mockElementRepository
        );
    }

    @Test
    public void inputToDomain_translatesNode() {
        String[] nodeFields = new String[] {"NODE","3327","","NO_RGB","16.6620","1.50000","22.3340"};

        Node actualNode = subject.inputToDomain(new Structure(), new Node(), nodeFields);

        assertThat(actualNode.getId(), is(equalTo(3327)));
        assertThat(actualNode.getX(), is(equalTo(16.6620)));
        assertThat(actualNode.getY(), is(equalTo(1.50000)));
        assertThat(actualNode.getZ(), is(equalTo(22.3340)));
    }

    @Test
    public void inputToDomain_translatesElement() {
        String[] elementFields = new String[] {"EL","2430","","NO_RGB","BEAM","211","40","3327","3326"};
        Node node1 = new Node().builder().id(3327).build();
        Node node2 = new Node().builder().id(3326).build();
        doReturn(node1).when(mockNodeRepository).findById(3327);
        doReturn(node2).when(mockNodeRepository).findById(3326);

        Element actualElement = subject.inputToDomain(new Structure(), new Element(), elementFields);

        assertThat(actualElement.getId(), is(equalTo(2430)));
        assertThat(actualElement.getGroupId(), is(equalTo(40)));
        assertThat(actualElement.getNode1(), is(equalTo(node1)));
        assertThat(actualElement.getNode2(), is(equalTo(node2)));
        assertThat(actualElement.getSectionPropertyId(), is(equalTo(211)));
        assertThat(actualElement.getType(), is(equalTo("BEAM")));
    }

    @Test
    public void inputToDomain_translatesForce() {
        String[] forceMomentFields = new String[] {"FORCE","2430","1","0","23965.4","-30.5844","109.418"};
        Element element = new Element().builder().id(2430).build();
        doReturn(element).when(mockElementRepository).findById(anyInt());

        ForceMoment actualForceMoment = subject.inputToDomain(new Structure(), new ForceMoment(), forceMomentFields);

        assertThat(actualForceMoment.getPosition(), is(equalTo(0)));
        assertThat(actualForceMoment.getResultCaseId(), is(equalTo(1)));
        assertThat(actualForceMoment.getFx(), is(equalTo(23965.4)));
        assertThat(actualForceMoment.getFy(), is(equalTo(-30.5844)));
        assertThat(actualForceMoment.getFz(), is(equalTo(109.418)));
        assertThat(actualForceMoment.getElement(), is(equalTo(element)));
    }
}