package hello;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "element")
public class ElementEntity implements Serializable {

    @JsonIgnore
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "structure_id")
    private StructureEntity structureEntity;

    @Id
    private int id;
    private String type;
    private int sectionPropertyId;
    private int groupId;

    @JsonIgnore
    @Column(name = "node_2_id")
    private int node1Id;

    @JsonIgnore
    @Column(name = "node_1_id")
    private int node2Id;

    @Transient
    private NodeEntity node1;

    @Transient
    private NodeEntity node2;

    @Transient
    private List<ForceMomentEntity> forceMoments;

}
