package az.code.springweb.exceptions;

public class StudentNotFound extends RuntimeException {
    public StudentNotFound() {
        super("No such student found");
    }
}
