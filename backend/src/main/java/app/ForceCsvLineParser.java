package app;

import org.springframework.stereotype.Component;

@Component
public class ForceCsvLineParser {

    public static ForceMomentEntity inputToDomain(String[] fields) {
        ForceMomentEntity forceMoment = new ForceMomentEntity();
        forceMoment.setElementId(Integer.parseInt(fields[1]));
        forceMoment.setResultCaseId(Integer.parseInt(fields[2]));
        forceMoment.setPosition(Integer.parseInt(fields[3]));
        forceMoment.setFx(Double.parseDouble(fields[4]));
        forceMoment.setFy(Double.parseDouble(fields[5]));
        forceMoment.setFz(Double.parseDouble(fields[6]));
        return forceMoment;
    }
}