package az.code.springweb.repositories;

import az.code.springweb.models.Grade;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GradeRepository extends CrudRepository<Grade, Long> {
    Optional<Grade> getGradeByIdAndStudentId(Long id, Long studentId);
}
