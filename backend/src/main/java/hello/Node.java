package hello;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Node {
    @JsonIgnore
    private Structure structure;
    private int id;
    private Double x;
    private Double y;
    private Double z;
}
