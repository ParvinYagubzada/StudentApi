package az.code.springweb.services;

public class Random {
    public static long id = 1;
    public static long gradeId = 1;

    public static long getId() {
        return id++;
    }

    public static long getGradeId() {
        return gradeId++;
    }
}
