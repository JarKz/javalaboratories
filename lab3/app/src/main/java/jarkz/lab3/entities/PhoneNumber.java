package jarkz.lab3.entities;

public class PhoneNumber {

	private int countryNumber;
	private int operatorNumber;
	private int phoneNumber;

	public PhoneNumber(int countryNumber, int operatorNumber, int innerPhoneNumber){
		this.countryNumber = countryNumber;
		this.operatorNumber = operatorNumber;
		phoneNumber = innerPhoneNumber;
	}

	public int getCountryNumber(){
		return countryNumber;
	}

	public void setCountryNumber(int countryNumber){
		this.countryNumber = countryNumber;
	}

	public int getOperatorNumber(){
		return operatorNumber;
	}

	public void setOperatorNumber(int operatorNumber){
		this.operatorNumber = operatorNumber;
	}

	public int getInnerPhoneNumber(){
		return phoneNumber;
	}

	public void setInnerPhoneNumber(int innerPhoneNumber){
		phoneNumber = innerPhoneNumber;
	}

	public String getFullPhoneNumberAsString(){
		return "" + countryNumber + operatorNumber + phoneNumber;
	}

	public long getFullPhoneNumberAsLong(){
		return Long.parseLong(getFullPhoneNumberAsString());
	}

	@Override
	public String toString() {
		return "PhoneNumber[+" + countryNumber + "-" + operatorNumber + "-" + phoneNumber + "]";
	}
}
