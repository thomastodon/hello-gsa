package hello;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="node")
public class NodeEntity implements Serializable{

    @Id
    @Column(name = "structure_id")
    private String structureId;

    @Id private int id;
    private Double x;
    private Double y;
    private Double z;
}
