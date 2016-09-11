package app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "structure")
public class StructureEntity {
    @Id
    String id;
    long postDate;
    int mass;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "structureEntity")
    private List<ElementEntity> elements;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "structureEntity")
    private List<NodeEntity> nodes;
}
