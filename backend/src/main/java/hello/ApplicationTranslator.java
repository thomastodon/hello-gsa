package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationTranslator {

    private final NodeRepository nodeRepository;

    @Autowired
    public ApplicationTranslator(
            NodeRepository nodeRepository
    ) {
        this.nodeRepository = nodeRepository;
    }

    public Node inputToDomain(Structure structure, Node node, String[] fields) {
        node.setStructure(structure);
        node.setId(Integer.parseInt(fields[1]));
        node.setX(Double.parseDouble(fields[4]));
        node.setY(Double.parseDouble(fields[5]));
        node.setZ(Double.parseDouble(fields[6]));
        return node;
    }

    public Element inputToDomain(Structure structure, Element element, String[] fields) {
        element.setStructure(structure);
        element.setId(Integer.parseInt(fields[1]));
        element.setType(fields[4]);
        element.setSectionPropertyId(Integer.parseInt(fields[5]));
        element.setGroupId(Integer.parseInt(fields[6]));
        element.setNode1(nodeRepository.findById(Integer.parseInt(fields[7])));
        element.setNode2(nodeRepository.findById(Integer.parseInt(fields[8])));
        return element;
    }
}
// TODO: break parser out into separate service for scaling?
