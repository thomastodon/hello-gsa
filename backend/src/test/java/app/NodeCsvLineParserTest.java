package app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class NodeCsvLineParserTest {

    @Test
    public void inputToDomain_translatesNode() {
        String[] nodeFields = new String[] {
                "NODE",
                "3327",
                "",
                "NO_RGB",
                "16.6620",
                "1.50000",
                "22.3340"
        };

        NodeEntity node = NodeCsvLineParser.inputToDomain(nodeFields);

        assertThat(node.getId(), is(equalTo(3327)));
        assertThat(node.getX(), is(equalTo(16.6620)));
        assertThat(node.getY(), is(equalTo(1.50000)));
        assertThat(node.getZ(), is(equalTo(22.3340)));
    }
}