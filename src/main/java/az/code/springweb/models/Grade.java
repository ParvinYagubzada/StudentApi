package az.code.springweb.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "grades", schema = "public")
@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "lesson_name")
    private String lessonName;
    private int grade;
    @Column(name = "creation_time")
    private LocalDateTime creationTime;
    @Column(name = "student_id")
    private Long studentId;

    @Override
    public boolean equals(Object obj) {
        boolean result = super.equals(obj);
        if (obj instanceof Grade) {
            result = ((Grade) obj).getId().equals(this.getId());
        }
        return result;
    }
}
