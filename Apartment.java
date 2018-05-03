package P3;

public class Apartment{

	private String street;
	private int aptNumber;
	private String city;
	private int zip;
	private int price;
	private int sqFootage;

	public String getStreet(){
		return this.street;
	}
	public int getAptNumber(){
		return this.aptNumber;
	}
	public String getCity(){
		return this.city;
	}
	public int getZip(){
		return this.zip;
	}
	public int getPrice(){
		return this.price;
	}
	public int getSqFootage(){
		return this.sqFootage;
	}
	public void setStreet(String street){
		this.street = street;
	}
	public void setAptNumber(int aptNumber){
		this.aptNumber = aptNumber;
	}
	public void setCity(String city){
		this.city = city;
	}
	public void setZip(int zip){
		this.zip = zip;
	}
	public void setPrice(int price){
		this.price = price;
	}
	public void setSqFootage(int sqFootage){
		this.sqFootage = sqFootage;
	}
}