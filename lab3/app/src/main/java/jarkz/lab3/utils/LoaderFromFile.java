package jarkz.lab3.utils;

import jarkz.lab3.entities.Abiturient;
import jarkz.lab3.entities.Address;
import jarkz.lab3.entities.Grades;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class LoaderFromFile {

	public List<Abiturient> loadFromFile(String filename) {
		InputStream stream = getByteStreamFromFile(filename);

		List<Abiturient> list = new ArrayList<>();
		try (Scanner scanner = new Scanner(stream, StandardCharsets.UTF_8)) {
			while (scanner.hasNext()) {
				if (scanner.nextLine().equals("Abiturient")){
					var abiturient = new Abiturient();
					setValuesForAbiturient(abiturient, scanner);
					list.add(abiturient);
				}
			}
		}
		return list;
	}

	private InputStream getByteStreamFromFile(String filename){
		InputStream stream = getClass().getClassLoader().getResourceAsStream(filename);
		if (stream == null)
			throw new IllegalArgumentException("File not found! Filename: " + filename);
		return stream;
	}

	private void setValuesForAbiturient(Abiturient abiturient, Scanner scanner){
		Method[] methods = Abiturient.class.getMethods();
		Map<String, Method> mapOfMethods = toMapOfMethods(methods);
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			if (line.isBlank())
				return;
			if (line.equals("homeAddress")){
				setHomeAddress(abiturient, scanner);
			} else if (line.equals("grades")){
				setGrades(abiturient, scanner);
			} else {
				String[] fieldAndValue = line.split(" ");
				String fieldName = fieldAndValue[0];
				String setterForField = "set" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);

				Method method = mapOfMethods.getOrDefault(setterForField, null);
				if (method != null){
					smartInvokeMethod(method, abiturient, fieldAndValue[1]);
				}
			}
		}
	}

	private void setHomeAddress(Abiturient abiturient, Scanner scanner){
		Method[] methods = Address.class.getMethods();
		Map<String, Method> mapOfMethods = toMapOfMethods(methods);
		Address homeAddress = new Address();
		abiturient.setHomeAddress(homeAddress);
		while (scanner.hasNext()){
			String line = scanner.nextLine();
			if (line.equals("endHomeAddress"))
				return;
			String[] fieldAndValue = line.split(" ");
			String fieldName = fieldAndValue[0];
			String setterForField = "set" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
			Method method = mapOfMethods.getOrDefault(setterForField, null);
			if (method != null){
				smartInvokeMethod(method, homeAddress, fieldAndValue[1]);
			}
		}
	}

	private void setGrades(Abiturient abiturient, Scanner scanner){
		Grades grades = new Grades();
		abiturient.setGrades(grades);
		while (scanner.hasNext()){
			String line = scanner.nextLine();
			if (line.equals("endGrades"))
				return;
			String[] subjectAndGrade = line.split(" ");
			String subject = subjectAndGrade[0];
			short grade = parseShort(subjectAndGrade[1]);
			grades.setGradeFor(subject, grade);
		}
	}

	private Map<String, Method> toMapOfMethods(Method[] methods){
		Map<String, Method> mapOfMethods = new HashMap<>();
		for (Method method : methods){
			Method probableMethod = mapOfMethods.getOrDefault(method.getName(), null);
			if (probableMethod == null)
				mapOfMethods.put(method.getName(), method);
			else {
				var types = method.getParameterTypes();
				if (types.length != 0 && types[0] == String.class)
					mapOfMethods.put(method.getName(), method);
			}
		}
		return mapOfMethods;
	}

	private <T> void smartInvokeMethod(Method method, T target, String... arguments){
		var types = method.getParameterTypes();
		if (types[0] == String.class) {
			invokeMethod(method, target, (Object[]) arguments);
		} else if (types[0] == int.class){
			var value = parseInt(arguments[0]);
			invokeMethod(method, target, value);
		} else if (types[0] == long.class){
			var value = parseLong(arguments[0]);
			invokeMethod(method, target, value);
		}
	}

	private short parseShort(String value){
		short result = -1;
		try {
			result = Short.parseShort(value);
		} catch (NumberFormatException e){
			e.printStackTrace();
		}
		return result;
	}

	private int parseInt(String value){
		int result = -1;
		try {
			result = Integer.parseInt(value);
		} catch (NumberFormatException e){
			e.printStackTrace();
		}
		return result;
	}

	private long parseLong(String value){
		long result = -1;
		try {
			result = Long.parseLong(value);
		} catch (NumberFormatException e){
			e.printStackTrace();
		}
		return result;
	}

	private <T> void invokeMethod(Method method, T target, Object... arguments){
		try {
			method.invoke(target, arguments);
		} catch (InvocationTargetException | IllegalAccessException e){
			e.printStackTrace();
		}
	}
}
