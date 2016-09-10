package hello;

import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Structure {
    private String id;
    private long postDate;
    private int mass;
    private HashSet<Element> elements;
}