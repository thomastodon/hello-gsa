package hello;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="force_moment")
public class ForceMomentEntity implements Serializable{
    @Id private String structureId;
    @Id private int elementId;
    @Id private int resultCaseId;
    @Id private int position;
    private double fx;
    private double fy;
    private double fz;
}