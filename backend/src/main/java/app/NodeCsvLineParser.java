package app;

import org.springframework.stereotype.Component;

@Component
public class NodeCsvLineParser {

    public static NodeEntity inputToDomain(String[] fields) {
        NodeEntity node = new NodeEntity();
        node.setId(Integer.parseInt(fields[1]));
        node.setX(Double.parseDouble(fields[4]));
        node.setY(Double.parseDouble(fields[5]));
        node.setZ(Double.parseDouble(fields[6]));
        return node;
    }
}