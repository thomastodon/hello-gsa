package app;

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

    // we're only relying on JPA to create tables based
    // on our entities. relationship annotations also create a few
    // basic indexes

    // inserts and queries are all managed with explicit sql

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
    private List<ForceMomentEntity> forceMoments;

    @Transient
    @JsonIgnore
    private int node1Id;

    @Transient
    @JsonIgnore
    private int node2Id;


}
