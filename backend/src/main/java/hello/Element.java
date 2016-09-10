package hello;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Element {
    @JsonIgnore
    private Structure structure;
    private int id;
    private String type;
    private int sectionPropertyId;
    private int groupId;
    private Node node1;
    private Node node2;
}
