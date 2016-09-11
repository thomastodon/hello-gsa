package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ApplicationService {

    private final StructureDao structureDao;

    @Autowired
    public ApplicationService(
            StructureDao structureDao) {
        this.structureDao = structureDao;
    }

    // TODO step1: executor? step 2: rabbit or other MQ?
    StructureEntity postStructure(String structureId, String input) {

        StructureEntity structureEntity = new StructureEntity();
        structureEntity.setId(structureId);

        List<String> lines = Arrays.asList(input.split("\\r?\\n"));

        Map<Integer, ElementEntity> elementMap = new HashMap<>();
        Map<Integer, NodeEntity> nodeMap = new HashMap<>();

        Iterator<String> iterator = lines.iterator();

        while (iterator.hasNext()) {
            String fields[] = iterator.next().split(",");

            switch (fields[0]) {
                case "NODE":
                    NodeEntity node = NodeCsvLineParser.inputToDomain(fields);
                    node.setStructureEntity(structureEntity);
                    nodeMap.put(node.getId(), node);
                    break;
                case "EL":
                    // TODO: don't pass the structure to the parser, just set it outside the method
                    // TODO: maybe have a separate StructureParser class
                    ElementEntity element = ElementCsvLineParser.inputToDomain(fields);
                    element.setNode1(nodeMap.get(element.getNode1Id()));
                    element.setNode2(nodeMap.get(element.getNode2Id()));
                    element.setForceMoments(new ArrayList<>());
                    element.setStructureEntity(structureEntity);
                    elementMap.put(element.getId(), element);
                    break;
                case "FORCE":
                    ForceMomentEntity forceMoment = ForceMomentCsvLineParser.inputToDomain(structureEntity.getId(), fields);
                    elementMap.get(forceMoment.getElementId()).getForceMoments().add(forceMoment);
                    break;
                case "MOMENT":
                    break;
            }
        }

        structureEntity.setElements(new ArrayList<>(elementMap.values()));
        structureEntity.setNodes(new ArrayList<>(nodeMap.values()));
        structureDao.save(structureEntity);

        return structureDao.findById(structureId);
    }

    StructureEntity getStructure(String id) {
        return structureDao.findById(id);
    }
}
