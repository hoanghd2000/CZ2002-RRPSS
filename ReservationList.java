
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ReservationList implements Serializable{

	private List<Reservation> reservationList;

	public ReservationList() {
		this.reservationList = new ArrayList<Reservation>();
	}

	public List<Reservation> getReservationList() {
		return this.reservationList;
	}

	/**
	 * 
	 * @param reservationList
	 */
	public void setReservationList(List<Reservation> reservationList) {
		this.reservationList = reservationList;
	}

	/**
	 * 
	 * @param dateTime
	 * @param paxNumber
	 * @param tableNumber
	 * @param name
	 * @param contact
	 */
	public void addNewRez(LocalDateTime dateTime, int paxNumber, int tableNumber, String name, String contact) {
		Reservation r = new Reservation(dateTime, paxNumber, tableNumber, name, contact);
		this.reservationList.add(r);
		// Sort the reservationList after any addition
		Collections.sort(reservationList, new Comparator<Reservation>() {
			  @Override
			  public int compare(Reservation r1, Reservation r2) {
			    return r1.getDateTime().compareTo(r2.getDateTime());
			  }
			});
	}

	/**
	 * 
	 * @param contact
	 */
	public Reservation findRez(String contact) {
		Iterator<Reservation> rl = reservationList.iterator();
		while(rl.hasNext()) {
			Reservation r = rl.next();
			if (r.getContact().compareTo(contact) == 0) {
				return r;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param reservation
	 */
	public void removeRez(Reservation reservation) {
		this.reservationList.remove(reservation);
	}

	public void printReservationList() {
		Iterator<Reservation> rl = this.reservationList.iterator();
		while(rl.hasNext()) {
			rl.next().print();
		}
	}
}