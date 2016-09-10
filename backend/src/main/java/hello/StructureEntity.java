package hello;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name="structure")
public class StructureEntity {
    @Id String id;
    long postDate;
    int mass;
}
