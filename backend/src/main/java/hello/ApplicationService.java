package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
    public Structure postStructure(String input) {

        StructureEntity structureEntity = new StructureEntity();
        structureEntity.setId("canopy");
        structureDao.save(structureEntity);

        String[] lines = input.split("\\r?\\n");
        int lineNumber = 0;

        Set<NodeEntity> nodes = new HashSet<>();
        while (true) {
            String line = lines[lineNumber];
            String fields[] = line.split(",");
            if (fields[0].equals("NODE")) {
                NodeEntity node = NodeCsvLineParser.inputToDomain(structureEntity.getId(), fields);
                nodes.add(node);
            } else if (fields[0].equals("EL")) {
                nodeDao.save(nodes);
                break;
            }
            lineNumber += 1;
        }

        Set<ElementEntity> elements = new HashSet<>();
        while (true) {
            String line = lines[lineNumber];
            String fields[] = line.split(",");
            if (fields[0].equals("EL")) {
                ElementEntity element = ElementCsvLineParser.inputToDomain(structureEntity.getId(), fields);
                elements.add(element);
            } else {
                elementDao.save(elements);
                break;
            }
            lineNumber += 1;
        }

        Set<ForceMomentEntity> forceMoments = new HashSet<>();
        while (true) {
            String line = lines[lineNumber];
            String fields[] = line.split(",");
            if (fields[0].equals("FORCE") && fields[3].equals("0")) {
                ForceMomentEntity forceMoment = ForceMomentCsvLineParser.inputToDomain(structureEntity.getId(), fields);
                forceMoments.add(forceMoment);
            } else if (fields[0].equals("MOMENT")) {
                forceMomentDao.save(forceMoments);
                break;
            }
            lineNumber += 1;
        }

        return structureDao.findById("canopy");
    }

    public Structure getStructure(String id) {
        return structureDao.findById(id);
    }
}

// TODO: bulk insertion of records? can JPA do this?
// TODO: how is JPA joining things?
// TODO: break out domain and entity layers to manage less code
