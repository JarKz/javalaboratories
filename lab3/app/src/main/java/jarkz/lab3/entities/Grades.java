package jarkz.lab3.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Grades {
	
	private final int MAX_GRADE = 10;
	private Map<String, Short> grades = new HashMap<>();

	public Grades(){
	}

	public Grades(Map<String, Short> grades){
		setGrades(grades);
	}

	public void setGrades(Map<String, Short> probableGrades){
		grades.clear();
		for (String subject : probableGrades.keySet()){
			setGradeFor(subject, probableGrades.get(subject));
		}
	}

	public void setGradeFor(String subject, short grade){
		if (grade < 1 || MAX_GRADE < grade)
			throw new IllegalArgumentException("Grade must be between one and ten inclusive! Current grade: " + grade);
		checkSubject(subject);
		grades.put(subject, grade);
	}

	public short getGradeFor(String subject){
		checkSubject(subject);
		return grades.get(subject);
	}

	public List<String> getAllSubjects(){
		return new ArrayList<>(grades.keySet());
	}

	public int getGradeSum(){
		return grades.values().stream().collect(Collectors.summingInt(g -> g));
	}

	private void checkSubject(String subject) throws IllegalArgumentException {
		if (subject.matches("\\d"))
			throw new IllegalArgumentException("Subject must not having digits! Current subject: " + subject);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("Grades[");
		grades.forEach((k, v) -> builder.append("{subject=" + k + ", grade=" + v + "}, "));
		builder.deleteCharAt(builder.length() - 1)
			.deleteCharAt(builder.length() - 1)
			.append("]");
		return builder.toString();
	}
}
