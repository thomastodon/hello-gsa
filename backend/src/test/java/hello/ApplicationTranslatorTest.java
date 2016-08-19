package hello;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class ApplicationTranslatorTest {


    @Test
    public void postStructure_insertsNodes() {

        Structure structure = new Structure();
        ArgumentCaptor<Structure> argumentCaptor = ArgumentCaptor.forClass(Structure.class);
        when(mockStructureRepository.save(argumentCaptor.capture())).thenReturn(structure);

        Set<Node> nodes = new HashSet<Node>();
        Node node3327 = Node.builder().id(3327).x(16.6620).y(1.50000).z(22.3340).build();
        Node node3326 = Node.builder().id(3326).x(13.8710).y(1.50000).z(21.6450).build();
        Node node3325 = Node.builder().id(3325).x(55.2660).y(37.0000).z(17.7580).build();
        Node node3324 = Node.builder().id(3324).x(0.000000).y(37.0000).z(3.09800).build();
        Node node3323 = Node.builder().id(3323).x(0.000000).y(37.0000).z(6.19600).build();
        Node node3322 = Node.builder().id(3322).x(0.150017).y(37.0000).z(9.28700).build();
        nodes.addAll(Arrays.asList(node3322, node3323, node3324, node3325, node3326, node3327));
//        Structure structure = new Structure();
//        structure.setNodes(nodes);

        subject.postStructure(input);

        assertThat(argumentCaptor.getValue().getNodes(), equalTo(nodes));
    }
}