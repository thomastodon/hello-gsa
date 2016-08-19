package hello;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="node")
public class Node {

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "structure_id")
    private Structure structure;

    @Id
    private int id;
    private Double x;
    private Double y;
    private Double z;

    @JsonIgnore
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "node1", cascade = CascadeType.ALL)
    private Set<Element> node1Elements;

    @JsonIgnore
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "node2", cascade = CascadeType.ALL)
    private Set<Element> node2Elements;
}
