package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ApplicationService {

    private final ForceMomentDao forceMomentDao;
    private final StructureDao structureDao;
    private final ElementDao elementDao;
    private final NodeDao nodeDao;

    @Autowired
    public ApplicationService(
            ForceMomentDao forceMomentDao, StructureDao structureDao, ElementDao elementDao, NodeDao nodeDao) {
        this.forceMomentDao = forceMomentDao;
        this.structureDao = structureDao;
        this.elementDao = elementDao;
        this.nodeDao = nodeDao;
    }

    // TODO step1: executor? step 2: rabbit or other MQ?
    public StructureEntity postStructure(String input) {

        StructureEntity structureEntity = new StructureEntity();
        structureEntity.setId("canopy");

        List<String> lines = Arrays.asList(input.split("\\r?\\n"));

        Map<Integer, ElementEntity> elementMap = new HashMap<>();
        Map<Integer, NodeEntity> nodeMap = new HashMap<>();
        Map<Integer, List<ForceMomentEntity>> forceMomentMap = new HashMap<>();

        Iterator<String> iterator = lines.iterator();
        while (iterator.hasNext()) {
            String fields[] = iterator.next().split(",");

            if (fields[0].equals("NODE")) {
                NodeEntity node = NodeCsvLineParser.inputToDomain(structureEntity.getId(), fields);
                nodeMap.put(node.getId(), node);
            } else if (fields[0].equals("EL")) {
                // TODO: don't pass the structure to the parser, just set it outside the method
                ElementEntity element = ElementCsvLineParser.inputToDomain(fields);
                element.setNode1(nodeMap.get(element.getNode1Id()));
                element.setNode2(nodeMap.get(element.getNode2Id()));
                element.setForceMoments(new ArrayList<>());
                element.setStructureEntity(structureEntity);
                elementMap.put(element.getId(), element);
            } else if (fields[0].equals("FORCE")) {
                ForceMomentEntity forceMoment = ForceMomentCsvLineParser.inputToDomain(structureEntity.getId(), fields);
                elementMap.get(forceMoment.getElementId()).getForceMoments().add(forceMoment);
            } else if (fields[0].equals("MOMENT")) {
                break;
            }
        }

        structureEntity.setElements(new ArrayList<>(elementMap.values()));

        structureDao.save(structureEntity);

        return structureDao.findById("canopy");
    }

    public StructureEntity getStructure(String id) {
        return structureDao.findById(id);
    }
}

// TODO: bulk insertion of records? can JPA do this?
// TODO: how is JPA joining things?
// TODO: break out domain and entity layers to manage less code
