/**
 * This class holds attributes concerning the Table class.
 * @author  SS10G5
 * @version 1.0
 * @since   2021-10-25
*/

import java.io.Serializable;

public class Table implements Serializable{

	private int tableID;
	private int size;
	private TableStatus status = TableStatus.VACANT;
	private static int count = 0;
	private ReservationList reservationList;

	/**
	 * 
	 * @param size
	 */
	public Table(int size) {
		this.tableID = ++count;
		this.size = size;
		this.reservationList = new ReservationList();
	}

	
	/** 
	 * @return int
	 */
	public int getTableID() {
		return this.tableID;
	}

	
	/** 
	 * @return TableStatus
	 */
	public TableStatus getStatus() {
		return this.status;
	}

	/**
	 * 
	 * @param status
	 */
	public void setStatus(TableStatus status) {
		this.status = status;
	}

	
	/** 
	 * @return int
	 */
	public static int getCount() {
		return count;
	}
	
	/**
	 * 
	 * @param count
	 */
	public static void setCount(int n) {
		count = n;
	}

	
	/** 
	 * @return int
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * 
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	
	/** 
	 * @return ReservationList
	 */
	public ReservationList getReservationList() {
		return this.reservationList;
	}

	/**
	 * 
	 * @param reservationList
	 */
	public void setReservationList(ReservationList reservationList) {
		this.reservationList = reservationList;
	}

}