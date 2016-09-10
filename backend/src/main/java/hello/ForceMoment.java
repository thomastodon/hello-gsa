package hello;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForceMoment {
    @JsonIgnore
    private Structure structure;
    private int elementId;
    private int resultCaseId;
    private int position;
    private double fx;
    private double fy;
    private double fz;

}

// TODO: composite primary key???