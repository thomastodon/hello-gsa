package hello;

import org.springframework.stereotype.Component;

@Component
public class ForceMomentCsvLineParser {

    public static ForceMoment inputToDomain(Structure structure, String[] fields) {
        ForceMoment forceMoment = new ForceMoment();
        forceMoment.setStructure(structure);
        forceMoment.setElementId(Integer.parseInt(fields[1]));
        forceMoment.setResultCaseId(Integer.parseInt(fields[2]));
        forceMoment.setPosition(Integer.parseInt(fields[3]));
        forceMoment.setFx(Double.parseDouble(fields[4]));
        forceMoment.setFy(Double.parseDouble(fields[5]));
        forceMoment.setFz(Double.parseDouble(fields[6]));
        return forceMoment;
    }
}
// TODO: break parser out into separate service for scaling?
