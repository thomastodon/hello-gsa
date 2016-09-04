package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ApplicationService {

    private final StructureRepository structureRepository;

    @Autowired
    public ApplicationService(
            StructureRepository structureRepository) {
        this.structureRepository = structureRepository;
    }

    // TODO step1: executor? step 2: rabbit or other MQ?
    public Structure postStructure(String input) {

        Structure structure = new Structure();
        structure.setId("canopy");

        Set<Node> nodes = new HashSet<Node>();
        String[] lines = input.split("\\r?\\n");
        int lineNumber = 0;
        while (true) {
            String line = lines[lineNumber];
            String fields[] = line.split(",");
            if (fields[0].equals("NODE")) {
                Node node = NodeCsvLineParser.inputToDomain(structure, fields);
                nodes.add(node);
            } else if (fields[0].equals("EL")) {
                structure.setNodes(nodes);
                break;
            }
            lineNumber += 1;
        }

        Set<Element> elements = new HashSet<Element>();
        while (true) {
            String line = lines[lineNumber];
            String fields[] = line.split(",");
            if (fields[0].equals("EL")) {
                Element element = ElementCsvLineParser.inputToDomain(structure, fields);
                elements.add(element);
            } else {
                structure.setElements(elements);
                break;
            }
            lineNumber += 1;
        }

        Set<ForceMoment> forces = new HashSet<>();
        while (true) {
            String line = lines[lineNumber];
            String fields[] = line.split(",");
            if (fields[0].equals("FORCE") && fields[3].equals("0")) {
                ForceMoment forceMoment = ForceMomentCsvLineParser.inputToDomain(structure, fields);
                forces.add(forceMoment);
            } else if (fields[0].equals("MOMENT")) {
                structure.setForceMoments(forces);
                break;
            }
            lineNumber += 1;
        }

        structureRepository.save(structure);

        return structure;
    }

    public Structure getStructure(String id) {
        return structureRepository.findOne(id);
    }
}

// TODO: bulk insertion of records? can JPA do this?
// TODO: how is JPA joining things?
// TODO: break out domain and entity layers to manage less code
