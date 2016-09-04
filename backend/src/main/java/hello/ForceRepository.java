package hello;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// TODO test these, make super class for tests and use for jdbc as well
// http://c2.com/cgi/wiki?AbstractTestCases
// lookup jb rainsberger's idea of contract tests
// make test database in mysql to validate things are getting saved
@Repository
public interface ForceRepository extends JpaRepository<ForceMoment, Long> {
    ForceMoment findByElementId(int elementId);
//    default ForceMoment findByElementId(String elementId) {
//        return
//    };
}