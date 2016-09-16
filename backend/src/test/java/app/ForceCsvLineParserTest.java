package app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ForceCsvLineParserTest {

    @Test
    public void inputToDomain_translatesForce() {
        String[] forceFields = new String[]{
                "FORCE",
                "2434",
                "13",
                "1",
                "12107.0",
                "56.8867",
                "276.379"
        };

        ForceEntity force = ForceCsvLineParser.inputToDomain(forceFields);

        assertEquals(force.getElementId(), 2434);
        assertEquals(force.getResultCaseId(), 13);
        assertEquals(force.getPosition(), 1);
        assertEquals(force.getFx(), 12107.0, 0);
        assertEquals(force.getFy(), 56.8867, 0);
        assertEquals(force.getFz(), 276.379, 0);
    }
}