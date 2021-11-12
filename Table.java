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

	public int getTableID() {
		return this.tableID;
	}

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