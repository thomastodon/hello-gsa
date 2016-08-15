package hello;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="element")
public class Element {

    @JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "structure_id")
    private Structure structure;

    @Id
    private int id;

    private String type;
    private int sectionPropertyId;
    private int groupId;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "node1Id")
    private Node node1;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "node2Id")
    private Node node2;
}

// TODO: could use ManyToMany and not have two node columns, or use ManyToOne with different columns
// TODO: way to create relationship between element and node by node_id rather than the node object?