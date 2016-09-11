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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "node_1_id",
                    referencedColumnName = "id",
                    insertable = false,
                    updatable = false
            ),
            @JoinColumn(name = "structure_id",
                    referencedColumnName = "structure_id",
                    insertable = false,
                    updatable = false
            )
    })
    private NodeEntity node1;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "node_2_id",
                    referencedColumnName = "id",
                    insertable = false,
                    updatable = false
            ),
            @JoinColumn(name = "structure_id",
                    referencedColumnName = "structure_id",
                    insertable = false,
                    updatable = false
            )
    })
    private NodeEntity node2;

    @Transient
    @JsonIgnore
    private int node1Id;

    @Transient
    @JsonIgnore
    private int node2Id;

    @Transient
    @JsonIgnore
    private List<ForceMomentEntity> forceMoments;

}
