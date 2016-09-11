package hello;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="node")
public class NodeEntity implements Serializable{
    @Id private String structureId;

    @Id private int id;
    private Double x;
    private Double y;
    private Double z;
}
