package hello;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElementRepository extends JpaRepository<Element, Long> {
    Element findById(int id);
}