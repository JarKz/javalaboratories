package jarkz.lab3.utils;

import java.util.Comparator;
import java.util.List;

import jarkz.lab3.entities.Abiturient;

public class Filterer {

    public static List<Abiturient> filterByAvgBadGrade(int averageBadGrade, List<Abiturient> abiturients){
        return abiturients.stream()
            .filter(a -> {
                double gradeSum = a.getGrades().getGradeSum();
                double gradeAvg = gradeSum / a.getGrades().getAllSubjects().size();
                return gradeAvg <= averageBadGrade;
            }).toList();
    }

    public static List<Abiturient> filterByGradeSum(int minimalGradeSum, List<Abiturient> abiturients){
        return abiturients.stream()
            .filter(a -> a.getGrades().getGradeSum() >= minimalGradeSum).toList();
    }

    public static List<Abiturient> sortByGradesAndGet(int count, List<Abiturient> abiturients){
        if (count < 0 || abiturients.size() < count){
            throw new IllegalArgumentException("Count must be between zero and max size of list! Current count: " + count);
        }
        return abiturients.stream()
            .sorted(Comparator.comparingInt(a -> ((Abiturient) a).getGrades().getGradeSum()).reversed())
            .limit(count)
            .toList();
    }

    public static List<Abiturient> sortByGradesAndGetWithMinimalGradeSum(int minimalGradeSum, List<Abiturient> abiturients){
        return abiturients.stream()
            .sorted(Comparator.comparingInt(a ->((Abiturient) a).getGrades().getGradeSum()).reversed())
            .filter(a -> a.getGrades().getGradeSum() >= minimalGradeSum)
            .toList();
    }
}
