//package hello;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//
//public class ForceCsvLineParserTest {
//
//    private ForceMomentCsvLineParser subject;
//
//    @Before
//    public void setup() {
//        subject = new ForceMomentCsvLineParser();
//    }
//
//    @Test
//    public void inputToDomain_translatesForce() {
//        String[] forceMomentFields = new String[]{
//                "FORCE",
//                "2434",
//                "13",
//                "1",
//                "12107.0",
//                "56.8867",
//                "276.379"
//        };
//
//        ForceMomentEntity forceMoment = subject.inputToDomain(new Structure(), forceMomentFields);
//
//        assertEquals(forceMoment.getElementId(), 2434);
//        assertEquals(forceMoment.getResultCaseId(), 13);
//        assertEquals(forceMoment.getPosition(), 1);
//        assertEquals(forceMoment.getFx(), 12107.0, 0);
//        assertEquals(forceMoment.getFy(), 56.8867, 0);
//        assertEquals(forceMoment.getFz(), 276.379, 0);
//    }
//}