package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ApplicationService {

    private final ApplicationTranslator applicationTranslator;
    private final StructureRepository structureRepository;
    private final ForceRepository forceRepository;

    @Autowired
    public ApplicationService(
            ApplicationTranslator applicationTranslator,
            StructureRepository structureRepository,
            ForceRepository forceRepository
    ) {
        this.applicationTranslator = applicationTranslator;
        this.structureRepository = structureRepository;
        this.forceRepository = forceRepository;
    }

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
                Node node = new Node();
                nodes.add(applicationTranslator.inputToDomain(structure, node, fields));
            } else if (fields[0].equals("EL")) {
                structure.setNodes(nodes);
                structureRepository.save(structure);
                break;
            }
            lineNumber += 1;
        }

        Set<Element> elements = new HashSet<Element>();
        while (true) {
            String line = lines[lineNumber];
            String fields[] = line.split(",");
            if (fields[0].equals("EL")) {
                Element element = new Element();
                elements.add(applicationTranslator.inputToDomain(structure, element, fields));
            } else  {
                structure.setElements(elements);
                structureRepository.save(structure);
                break;
            }
            lineNumber += 1;
        }

        while (true) {
            String line = lines[lineNumber];
            String fields[] = line.split(",");
            if (fields[0].equals("FORCE") && fields[3].equals("0")) {
                ForceMoment forceMoment = new ForceMoment();
                forceMoment = applicationTranslator.inputToDomain(structure, forceMoment, fields);
                forceRepository.save(forceMoment);
            } else if (fields[0].equals("MOMENT")) {
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