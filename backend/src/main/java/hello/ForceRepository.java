package hello;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForceRepository extends JpaRepository<ForceMoment, Long> {
    ForceMoment findByElement(Element element);
}