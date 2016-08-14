package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ApplicationService {

    private final ApplicationTranslator applicationTranslator;
    private final StructureRepository structureRepository;

    @Autowired
    public ApplicationService(
            ApplicationTranslator applicationTranslator,
            StructureRepository structureRepository
    ) {
        this.applicationTranslator = applicationTranslator;
        this.structureRepository = structureRepository;
    }

    public Structure postStructure(String input) {
        Structure structure = new Structure();
        structure.setId("canopy");
        Set<Node> nodeEntities = new HashSet<Node>();
        Set<Element> elementEntities = new HashSet<Element>();

        String[] lines = input.split("\\r?\\n");
        int lineNumber = 0;
        while (true) {
            String line = lines[lineNumber];
            String fields[] = line.split(",");
            if (fields[0].equals("NODE")) {
                Node node = new Node();
                nodeEntities.add(applicationTranslator.inputToDomain(structure, node, fields));
            } else if (fields[0].equals("EL")) {
                structure.setNodes(nodeEntities);
                structureRepository.save(structure);
                break;
            }
            lineNumber += 1;
        }

        while (true) {
            String line = lines[lineNumber];
            String fields[] = line.split(",");
            if (fields[0].equals("EL")) {
                Element element = new Element();
                elementEntities.add(applicationTranslator.inputToDomain(structure, element, fields));
            } else  {
                structure.setElements(elementEntities);
                structureRepository.save(structure);
                break;
            }
            lineNumber += 1;
        }

        return null;
    }

    public Structure getStructure(String id) {
        return structureRepository.findOne(id);
    }
}

// TODO: bulk insertion of records? can JPA do this?
// TODO: how is JPA joining things?
// TODO: merged domain and entity layers to manage less code
// TODO: Is this necessary when we have control of our persistent stores?
// TODO: return json of structure after post
