package az.code.springweb.services;

public class Random {
    public static int id = 1;
    public static int gradeId = 1;

    public static int getId() {
        return id++;
    }

    public static int getGradeId() {
        return gradeId++;
    }
}
