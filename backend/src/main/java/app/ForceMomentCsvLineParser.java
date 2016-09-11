package app;

import org.springframework.stereotype.Component;

@Component
public class ForceMomentCsvLineParser {

    public static ForceMomentEntity inputToDomain(String structureId, String[] fields) {
        ForceMomentEntity forceMoment = new ForceMomentEntity();
        forceMoment.setStructureId(structureId);
        forceMoment.setElementId(Integer.parseInt(fields[1]));
        forceMoment.setResultCaseId(Integer.parseInt(fields[2]));
        forceMoment.setPosition(Integer.parseInt(fields[3]));
        forceMoment.setFx(Double.parseDouble(fields[4]));
        forceMoment.setFy(Double.parseDouble(fields[5]));
        forceMoment.setFz(Double.parseDouble(fields[6]));
        return forceMoment;
    }
}

// TODO: can't set element on forceMoment without actual object or
// TODO: without including the entity manager ...
// TODO: `Department department = entityManager.getReference(Department.class, departmentId);
// TODO: user.setDepartment(department);`
// TODO: ... I would want to manage this at a repository layer somewhere, not in the parser
