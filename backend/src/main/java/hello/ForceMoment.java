package hello;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="force_moment")
public class ForceMoment {

    @Id @GeneratedValue long id;

    @JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "structure_id")
    private Structure structure;

    @JsonIgnore
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="element_id")
    private Element element;

    private int resultCaseId;
    private int position;
    private Double fx;
    private Double fy;
    private Double fz;
}

// TODO: composite primary key???