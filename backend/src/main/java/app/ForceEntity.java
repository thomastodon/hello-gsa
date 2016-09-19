package app;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "force_moment")
public class ForceEntity implements Serializable {
    @JsonIgnore
    @Id
    private String structureId;

    @JsonIgnore
    @Id
    private int elementId;
    @Id
    private int resultCaseId;
    @Id
    private int position;
    private double fx;
    private double fy;
    private double fz;
}