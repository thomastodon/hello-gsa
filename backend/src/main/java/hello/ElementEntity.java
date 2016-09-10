package hello;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="element")
public class ElementEntity implements Serializable {
    @Id private String structureId;
    @Id private int id;
    private String type;
    private int sectionPropertyId;
    private int groupId;
    @Column(name="node_1_id") private int node1Id;
    @Column(name="node_2_id") private int node2Id;
}
