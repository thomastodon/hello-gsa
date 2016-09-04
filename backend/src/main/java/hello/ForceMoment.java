package hello;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="force_moment")
public class ForceMoment {

    @Id @GeneratedValue long id;

    @JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "structure_id")
    private Structure structure;

    private int elementId;
    private int resultCaseId;
    private int position;
    private double fx;
    private double fy;
    private double fz;

}

// TODO: composite primary key???