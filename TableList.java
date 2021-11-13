/**
 * Represents an HashTable of Table objects. This class ensures the removal of
 * Reservation after XX minutes. A tableList must have at least 1 table object.
 * @author  SS10G5
 * @version 1.0
 * @since   2021-10-25
*/

import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class TableList implements Serializable {

	private Hashtable<Integer, Table> tableList;

	
	/** 
	 * @return Hashtable<Integer, Table>
	 */
	public Hashtable<Integer, Table> getTableList() {
		return this.tableList;
	}

	/**
	 * 
	 * @param tableList
	 */
	public void setTableList(Hashtable<Integer, Table> tableList) {
		this.tableList = tableList;
	}

	public TableList() {
		this.tableList = new Hashtable<Integer, Table>();
	}

	public void addTable() {
		
		System.out.printf("Input the size of the new table: ");
		int size = Integer.parseInt(RestaurantApp.s.nextLine());
		while (size % 2 == 1 || size < 2 || size > 10) {
			System.out.println("Table size must be an even number, min 2, max 10");
			System.out.printf("Input the size of the new table: ");
			size = Integer.parseInt(RestaurantApp.s.nextLine());
		}
		Table t = new Table(size);
		this.tableList.put(t.getTableID(), t);
	}

	public void removeTable() {
		System.out.printf("Input the ID of the table to be removed: ");
		int tableID = Integer.parseInt(RestaurantApp.s.nextLine());
		this.tableList.remove(tableID);
	}
	
	/**
	 * 
	 * @param tableID
	 */
	
	public boolean exists(int tableID) {
		return tableList.containsKey(tableID);
	}

	/**
	 * 
	 * @param dateTime
	 * @param paxNumber
	 */
	public int checkRezAvailability(LocalDateTime dateTime, int paxNumber) {
		if (dateTime.toLocalDate().compareTo(LocalDate.now()) <= 0) // Reservation can only be made in advance && check for invalid dateTime input (e.g. dateTime is in the past)
			return -1;
		if (paxNumber > 10) // Assume that 1 customer can reserve at most 1 table
			return -1;
		int minTableSize = paxNumber + (paxNumber % 2);
		for (int i = minTableSize; i <= 10; i = i + 2) { // for each TableSize (order of assigning table: assign the smallest available table)
			Enumeration<Integer> tableIds = tableList.keys();
			while(tableIds.hasMoreElements()) { // for each table of size i
				int key = tableIds.nextElement();
				Table table = tableList.get(key);
				boolean found = false;
				if (table.getSize() == i) {
					List<Reservation> rl = table.getReservationList().getReservationList();
					Iterator<Reservation> rezs = rl.iterator();
					if (!rezs.hasNext()) // if the reservationList for that table is empty, can book
						return table.getTableID();
					while(rezs.hasNext()) {
						Reservation rez = rezs.next();
						// Cannot make reservation if it is within 1.5 hr from/to an existing reservation
						if (rez.getDateTime().compareTo(dateTime) < 0 && rez.getDateTime().compareTo(dateTime.minusMinutes(90)) > 0)
							break;
						else if (rez.getDateTime().compareTo(dateTime) > 0 && rez.getDateTime().compareTo(dateTime.plusMinutes(90)) < 0)
							break;
						if(!rezs.hasNext())
							found = true;
					}
					if(found)
						return table.getTableID();
				}
			}
		}
		return -1;
	}

	/**
	 * 
	 * @param dateTime
	 * @param paxNumber
	 * @param name
	 * @param contact
	 */
	public int createNewRez(LocalDateTime dateTime, int paxNumber, String name, String contact) {
		int tableId = this.checkRezAvailability(dateTime, paxNumber);
		if (tableId != -1)
			tableList.get(tableId).getReservationList().addNewRez(dateTime, paxNumber, tableId, name, contact);
		return tableId;
	}

	/**
	 * 
	 * @param contact
	 */
	public Reservation findRez(String contact) { //Assume that each customer making reservation has a unique contact number and can only have a max. of 1 reservation at any time.
		Enumeration<Integer> tableIds = tableList.keys();
		while(tableIds.hasMoreElements()) { // iterate through the tables
			int tableId = tableIds.nextElement();
			Table table = tableList.get(tableId);
			ReservationList rl = table.getReservationList();
			Reservation r = rl.findRez(contact);
			if (r != null)
				return r;
		}
		return null;
	}

	/**
	 * 
	 * @param reservation
	 */
	public void removeRez(Reservation reservation) {
		int tableId = reservation.getTableNumber();
		tableList.get(tableId).getReservationList().removeRez(reservation);
	}
	
	public void printAllRezs() {
		boolean empty = true;
		Enumeration<Integer> tableIds = tableList.keys();
		while(tableIds.hasMoreElements()) { // iterate through each table
			int tableId = tableIds.nextElement();
			Table table = tableList.get(tableId);
			
			table.getReservationList().printReservationList();
			empty = table.getReservationList().getReservationList().isEmpty() ? empty : false;
		}
		if (empty)
			System.out.println("No reservation has been made");
	}
	
	
	/** 
	 * @param 0
	 */
	public void updateAllRezs() { // implement the 'expiry period'
		Enumeration<Integer> tableIds = tableList.keys();
		while(tableIds.hasMoreElements()) { // iterate through each table
			int tableId = tableIds.nextElement();
			Table table = tableList.get(tableId);
			
			// After 'expiry period = 15mins', remove the expired reservation and update table status to VACANT
			if (table.getReservationList().getReservationList().size() > 0 && table.getReservationList().getReservationList().get(0).getDateTime().compareTo(LocalDateTime.now().minusMinutes(15)) <= 0) {
				table.getReservationList().getReservationList().remove(0);
				if (table.getStatus() == TableStatus.RESERVED)
					table.setStatus(TableStatus.VACANT);
			}
		}
	}

	public void checkTableAvailability() {
		Enumeration<Integer> tableIds = tableList.keys();
		while(tableIds.hasMoreElements()) { // iterate through each table
			int tableId = tableIds.nextElement();
			Table table = tableList.get(tableId);
			
			// Update table status to RESERVED if reservation coming up within 1 hour
			if (table.getReservationList().getReservationList().size() > 0 && table.getReservationList().getReservationList().get(0).getDateTime().compareTo(LocalDateTime.now().plusHours(1)) <= 0) {
				if (table.getStatus() == TableStatus.VACANT)
					table.setStatus(TableStatus.RESERVED);
			}
			
			// After 'expiry period = 15mins', remove the expired reservation and update table status to VACANT
			if (table.getReservationList().getReservationList().size() > 0 && table.getReservationList().getReservationList().get(0).getDateTime().compareTo(LocalDateTime.now().minusMinutes(15)) <= 0) {
				table.getReservationList().getReservationList().remove(0);
				if (table.getStatus() == TableStatus.RESERVED)
					table.setStatus(TableStatus.VACANT);
			}
			
			// Print out each table's availability
			System.out.printf("Table %d: %s\n", table.getTableID(), table.getStatus().toString());
		}
	}
	
	
	/** 
	 * @param LocalDateTime.ofInstant(RestaurantApp.clockat.instant()
	 * @param table.getReservationList().getReservationList().get(0).getDateTime().compareTo(LocalDateTime.ofInstant(RestaurantApp.clockat.instant()
	 * @param 0
	 */
	public void updateAllRezsTest() { // implement the 'expiry period'
		System.out.println("Time: " + LocalDateTime.ofInstant(RestaurantApp.clockat.instant(), ZoneOffset.UTC));
		Enumeration<Integer> tableIds = tableList.keys();
		while(tableIds.hasMoreElements()) { // iterate through each table
			int tableId = tableIds.nextElement();
			Table table = tableList.get(tableId);
			
			// After 'expiry period = 15mins', remove the expired reservation and update table status to VACANT
			if (table.getReservationList().getReservationList().size() > 0 && table.getReservationList().getReservationList().get(0).getDateTime().compareTo(LocalDateTime.ofInstant(RestaurantApp.clockat.instant(), ZoneOffset.UTC).minusMinutes(15)) <= 0) {
				table.getReservationList().getReservationList().remove(0);
				if (table.getStatus() == TableStatus.RESERVED)
					table.setStatus(TableStatus.VACANT);
			}
		}
	}

	public void checkTableAvailabilityTest1() {
//		System.out.println(RestaurantApp.clock.instant());
//		System.out.println(RestaurantApp.clockbf.instant());
		System.out.println("Time: " + LocalDateTime.ofInstant(RestaurantApp.clockbf.instant(), ZoneOffset.UTC));
		
		Enumeration<Integer> tableIds = tableList.keys();
		while(tableIds.hasMoreElements()) { // iterate through each table
			int tableId = tableIds.nextElement();
			Table table = tableList.get(tableId);
			
			// Update table status to RESERVED if reservation coming up within 1 hour
			if (table.getReservationList().getReservationList().size() > 0 && table.getReservationList().getReservationList().get(0).getDateTime().compareTo(LocalDateTime.ofInstant(RestaurantApp.clockbf.instant(), ZoneOffset.UTC).plusHours(1)) <= 0) {
				if (table.getStatus() == TableStatus.VACANT)
					table.setStatus(TableStatus.RESERVED);
			}
			
			// After 'expiry period = 15mins', remove the expired reservation and update table status to VACANT
			if (table.getReservationList().getReservationList().size() > 0 && table.getReservationList().getReservationList().get(0).getDateTime().compareTo(LocalDateTime.ofInstant(RestaurantApp.clockbf.instant(), ZoneOffset.UTC).minusMinutes(15)) <= 0) {
				table.getReservationList().getReservationList().remove(0);
				if (table.getStatus() == TableStatus.RESERVED)
					table.setStatus(TableStatus.VACANT);
			}
			// Print out each table's availability
			System.out.printf("Table %d: %s\n", table.getTableID(), table.getStatus().toString());
		}
	}
	
	public void checkTableAvailabilityTest2() {
//		System.out.println(RestaurantApp.clock.instant());
//		System.out.println(RestaurantApp.clockbf.instant());
		System.out.println("Time: " + LocalDateTime.ofInstant(RestaurantApp.clockat.instant(), ZoneOffset.UTC));
		
		Enumeration<Integer> tableIds = tableList.keys();
		while(tableIds.hasMoreElements()) { // iterate through each table
			int tableId = tableIds.nextElement();
			Table table = tableList.get(tableId);
			
			// Update table status to RESERVED if reservation coming up within 1 hour
			if (table.getReservationList().getReservationList().size() > 0 && table.getReservationList().getReservationList().get(0).getDateTime().compareTo(LocalDateTime.ofInstant(RestaurantApp.clockat.instant(), ZoneOffset.UTC).plusHours(1)) <= 0) {
				if (table.getStatus() == TableStatus.VACANT)
					table.setStatus(TableStatus.RESERVED);
			}
			
			// After 'expiry period = 15mins', remove the expired reservation and update table status to VACANT
			if (table.getReservationList().getReservationList().size() > 0 && table.getReservationList().getReservationList().get(0).getDateTime().compareTo(LocalDateTime.ofInstant(RestaurantApp.clockat.instant(), ZoneOffset.UTC).minusMinutes(15)) <= 0) {
				table.getReservationList().getReservationList().remove(0);
				if (table.getStatus() == TableStatus.RESERVED)
					table.setStatus(TableStatus.VACANT);
			}
			// Print out each table's availability
			System.out.printf("Table %d: %s\n", table.getTableID(), table.getStatus().toString());
		}
	}
	
	public void print() {
		Enumeration<Integer> tableIds = tableList.keys();
		while(tableIds.hasMoreElements()) { // iterate through each table
			int tableId = tableIds.nextElement();
			Table table = tableList.get(tableId);
			
			// Print out each table's availability
			System.out.printf("Table %d: Size: %d, Count: %d \n", table.getTableID(), table.getSize(), Table.getCount());
		}
	}
}