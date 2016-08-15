package hello;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="structure")
public class Structure {
    @Id
    private String id;
    private long postDate;
    private int mass;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "structure", cascade = CascadeType.ALL)
    private Set<Node> nodes;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "structure", cascade = CascadeType.ALL)
    private Set<Element> elements;
}

// TODO: can't use @Data with my one-to-many relationship