package az.code.springweb.dtos;

import az.code.springweb.models.Student;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class GradeDTO {
    private Long id;
    private String lessonName;
    private int grade;
    private LocalDateTime creationTime;
    private StudentDTO student;
}
