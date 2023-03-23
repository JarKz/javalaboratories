package jarkz.lab3.entities;

public class Address {
	private String country;
	private String city;
	private String street;
	private String home;
	private int appartements;
	private int postCode;

	public Address(){
	}

	public Address(
		String country,
		String city,
		String street,
		String home,
		int appartements,
		int postCode
	){
		this.country = country;
		this.city = city;
		this.street = street;
		this.home = home;
		this.appartements = appartements;
		this.postCode = postCode;
	}

	public String getCountry(){
		return country;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCity(){
		return city;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getStreet(){
		return street;
	}

	public void setStreet(String street){
		this.street = street;
	}

	public String getHome(){
		return home;
	}

	public void setHome(String home){
		this.home = home;
	}

	public int getAppartements(){
		return appartements;
	}

	public void setAppartements(int appartements){
		this.appartements = appartements;
	}

	public int getPostCode(){
		return postCode;
	}

	public void setPostCode(int postCode){
		this.postCode = postCode;
	}

	@Override
	public String toString() {
		return "Address[country=" + country + ", "
		+ "city=" +  city + ", "
		+ "street=" + street + ", "
		+ "home=" + home + ", "
		+ "appartements=" + appartements + ", "
		+ "postCode=" + postCode + "]";
	}
}
