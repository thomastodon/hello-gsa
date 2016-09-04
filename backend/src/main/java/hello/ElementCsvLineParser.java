package hello;

import org.springframework.stereotype.Component;

// TODO make separate parsers for each object

@Component
public class ElementCsvLineParser {

    public static Element inputToDomain(Structure structure, String[] fields) {
        Element element = new Element();
        element.setStructure(structure);
        element.setId(Integer.parseInt(fields[1]));
        element.setType(fields[4]);
        element.setSectionPropertyId(Integer.parseInt(fields[5]));
        element.setGroupId(Integer.parseInt(fields[6]));
        element.setNode1Id(Integer.parseInt(fields[7]));
        element.setNode2Id(Integer.parseInt(fields[8]));
        return element;
    }
}