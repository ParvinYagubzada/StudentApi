package az.code.springweb.exceptions;

public class GradeNotFound extends RuntimeException {
    public GradeNotFound() {
        super("No such grade found");
    }
}
