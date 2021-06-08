package az.code.springweb.models;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    private int id;
    private String lessonName;
    private int grade;
    private LocalDateTime creationTime;

    @Override
    public boolean equals(Object obj) {
        boolean result = super.equals(obj);
        if (obj instanceof Grade) {
            result = ((Grade) obj).getId() == this.getId();
        }
        return result;
    }
}
