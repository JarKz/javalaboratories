package jarkz.lab3.entities;

import java.util.Arrays;

public class Abiturient {

	private long id;
	private String firstName;
	private String lastName;
	private String patronymic;

	private Address homeAddress;
	private PhoneNumber phoneNumber;
	private Grades grades;

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}

	public Address getHomeAddress() {
		return homeAddress;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber){
		String[] splittedPhoneNumber = phoneNumber.split("-");
		if (splittedPhoneNumber.length != 3)
			throw new IllegalArgumentException("Invalid phone number: " + phoneNumber);
		int[] phoneNumberAsArray = Arrays.stream(splittedPhoneNumber).filter(s -> {
			try {
				Integer.parseInt(s);
				return true;
			} catch (NumberFormatException e){
				return false;
			}
		}).mapToInt(s -> Integer.parseInt(s)).toArray();
		this.phoneNumber = new PhoneNumber(phoneNumberAsArray[0], phoneNumberAsArray[1], phoneNumberAsArray[2]);
	}

	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	public void setGrades(Grades grades) {
		this.grades = grades;
	}

	public Grades getGrades() {
		return grades;
	}

	@Override
	public String toString() {
		return "Abiturient[id=" + id + ", "
			+ "firstName=" + firstName + ", "
			+ "lastName=" + lastName + ", "
			+ "patronymic=" + patronymic + ", "
			+ "homeAddress=" + homeAddress.toString() + ", "
			+ "phoneNumber=" + phoneNumber.toString() + ", "
			+ "grades=" + grades.toString() + "]";
	}
}
