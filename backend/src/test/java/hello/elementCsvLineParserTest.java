package hello;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class elementCsvLineParserTest {

    private ElementCsvLineParser subject;

    @Before
    public void setup() {
        subject = new ElementCsvLineParser();
    }

    @Test
    public void inputToDomain_translatesElement() {
        String[] elementFields = new String[]{
                "EL",
                "2430",
                "",
                "NO_RGB",
                "BEAM",
                "211",
                "40",
                "3327",
                "3326"
        };

        Element element = subject.inputToDomain(new Structure(), elementFields);

        assertEquals(element.getId(), 2430);
        assertEquals(element.getGroupId(), 40);
        assertEquals(element.getNode1Id(), 3327);
        assertEquals(element.getNode2Id(), 3326);
        assertEquals(element.getSectionPropertyId(), 211);
        assertEquals(element.getType(), "BEAM");
    }
}