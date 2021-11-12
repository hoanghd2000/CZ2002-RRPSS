
import java.io.Serializable;
import java.time.LocalDateTime;

public class Reservation implements Serializable{

	private LocalDateTime dateTime;
	private int paxNumber;
	private int tableNumber;
	private String name;
	private String contact;

	/**
	 * 
	 * @param dateTime
	 * @param paxNumber
	 * @param tableNumber
	 * @param name
	 * @param contact
	 */
	public Reservation(LocalDateTime dateTime, int paxNumber, int tableNumber, String name, String contact) {
		this.dateTime = dateTime;
		this.paxNumber = paxNumber;
		this.tableNumber = tableNumber;
		this.name = name;
		this.contact = contact;
	}

	public LocalDateTime getDateTime() {
		return this.dateTime;
	}

	/**
	 * 
	 * @param dateTime
	 */
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public int getPaxNumber() {
		return this.paxNumber;
	}

	/**
	 * 
	 * @param paxNumber
	 */
	public void setPaxNumber(int paxNumber) {
		this.paxNumber = paxNumber;
	}

	public int getTableNumber() {
		return this.tableNumber;
	}

	/**
	 * 
	 * @param tableNumber
	 */
	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return this.contact;
	}

	/**
	 * 
	 * @param contact
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	public void print() {
		System.out.printf("Reservation for %s, %s: Table %d for %d pax at %s\n", name, contact, tableNumber, paxNumber, dateTime.toString());
	}

}