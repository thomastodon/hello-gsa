package app;

import org.springframework.stereotype.Component;

@Component
public class ForceCsvLineParser {

    public static ForceEntity inputToDomain(String[] fields) {
        ForceEntity force = new ForceEntity();
        force.setElementId(Integer.parseInt(fields[1]));
        force.setResultCaseId(Integer.parseInt(fields[2]));
        force.setPosition(Integer.parseInt(fields[3]));
        force.setFx(Double.parseDouble(fields[4]));
        force.setFy(Double.parseDouble(fields[5]));
        force.setFz(Double.parseDouble(fields[6]));
        return force;
    }
}