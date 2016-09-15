package app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ElementCsvLineParserTest {

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

        ElementEntity element = ElementCsvLineParser.inputToDomain(elementFields);

        assertEquals(element.getId(), 2430);
        assertEquals(element.getGroupId(), 40);
        assertEquals(element.getNode1Id(), 3327);
        assertEquals(element.getNode2Id(), 3326);
        assertEquals(element.getSectionPropertyId(), 211);
        assertEquals(element.getType(), "BEAM");
    }
}