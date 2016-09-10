package hello;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name="node")
public class NodeEntity implements Serializable{
    @Id private String structureId;
    @Id private int id;
    private Double x;
    private Double y;
    private Double z;
}
