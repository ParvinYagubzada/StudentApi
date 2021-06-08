package az.code.springweb.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private int id;
    private String name;
    private String surname;
    private List<Grade> grades;

    @Override
    public boolean equals(Object obj) {
        boolean result = super.equals(obj);
        if (obj instanceof Student) {
            result = ((Student) obj).getId() == this.getId();
        }
        return result;
    }
}
