package hello;

import org.omg.CORBA.DoubleHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationTranslator {

    private final NodeRepository nodeRepository;
    private final ElementRepository elementRepository;

    @Autowired
    public ApplicationTranslator(
            NodeRepository nodeRepository,
            ElementRepository elementRepository
    ) {
        this.nodeRepository = nodeRepository;
        this.elementRepository = elementRepository;
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

    public ForceMoment inputToDomain(Structure structure, ForceMoment forceMoment, String[] fields) {
//        force.setStructure(structure);
        forceMoment.setElement(elementRepository.findById(Integer.parseInt(fields[1])));
        forceMoment.setResultCaseId(Integer.parseInt(fields[2]));
        forceMoment.setPosition(Integer.parseInt(fields[3]));
        forceMoment.setFx(Double.parseDouble(fields[4]));
        forceMoment.setFy(Double.parseDouble(fields[5]));
        forceMoment.setFz(Double.parseDouble(fields[6]));
        return forceMoment;
    }
}
// TODO: break parser out into separate service for scaling?
