import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;

enum TableStatus{OCCUPIED, VACANT, RESERVED;}

public class RestaurantApp {
	public static TableList tableList = new TableList();
	public static void main(String args[]) {
		
		Scanner s = new Scanner(System.in);
		
		initializeTableList();
		tableList.print();
		System.out.println("(1) Create reservation booking");
		System.out.println("(2) Check/Remove reservation booking");
		System.out.println("(3) Check table availability");
		System.out.println("(4) Print order invoice");
		System.out.println("(5) Place an order");
		System.out.println("(6) Add a table");
		System.out.println("(7) Remove a table");
		System.out.println("(8) Print tableList");
		System.out.println("(9) Exit");
		System.out.print("Enter the number of your choice: ");
		int c = Integer.parseInt(s.next());
		
		while (1 <= c && c <= 8) {
			switch(c) {
				case 1:
					System.out.print("Enter date (YYYY-MM-DD): ");
					String date = s.next();
					System.out.print("Enter time (hh:mm:ss): ");
					String time = s.next();
					System.out.print("Enter number of pax: ");
					int paxNumber = Integer.parseInt(s.next());
					System.out.print("Enter customer's name: ");
					String name = s.next();
					System.out.print("Enter customer's contact no.: ");
					String contact = s.next();
					String dateTime = date + "T" + time;
					LocalDateTime dateTime1 = LocalDateTime.parse(dateTime);
					int tableId = tableList.createNewRez(dateTime1, paxNumber, name, contact);
					if (tableId == -1)
						System.out.println("No available table");
					else
						System.out.printf("Reservation created successfully! Table reserved: %d\n", tableId);
					break;
				case 2:
					tableList.updateAllRezs();
					System.out.println("(1) Display all current reservations");
					System.out.println("(2) Check/Remove a reservation");
					int d = Integer.parseInt(s.next());
					switch(d) {
					 	case 1:
					 		tableList.printAllRezs();
					 		break;
					 	case 2:
					 		System.out.print("Retrieve reservation for customer of contact no.: ");
					 		contact = s.next();
					 		Reservation rez = tableList.findRez(contact);
					 		if (rez == null)
					 			System.out.println("No reservation created for that contact no.");
					 		else {
					 			rez.print();
					 			System.out.println("Do you want to remove this reservation? (Y/N)");
					 			String choice = s.next();
					 			if (choice.equalsIgnoreCase("Y")) {
					 				Table table = tableList.getTableList().get(rez.getTableNumber());
						 			if (rez.getDateTime().compareTo(LocalDateTime.now().plusHours(1)) <= 0)
										if (table.getStatus() == TableStatus.RESERVED)
											table.setStatus(TableStatus.VACANT);
						 			tableList.removeRez(rez);
					 			}
					 		}
					}
					break;
				case 3:
					tableList.checkTableAvailability();
					break;
				case 4:
					//TODO - print invoice
					// set the status of the table from which the order is from OCCUPIED to VACANT
					break;
				case 5: 
					//TODO - place an order
					// set the status of the table from which the order is from VACANT/RESERVED to OCCUPIED
					break;
				case 6:
					System.out.printf("Input the size of the new table: ");
					int size = Integer.parseInt(s.next());
					tableList.addTable(size);
					break;
				case 7:
					System.out.printf("Input the ID of the table to be removed: ");
					int rID = Integer.parseInt(s.next());
					tableList.removeTable(rID);
					break;
				case 8:
					tableList.print();
					break;
				default:
					break;
			}
			
			System.out.println("(1) Create reservation booking");
			System.out.println("(2) Check/Remove reservation booking");
			System.out.println("(3) Check table availability");
			System.out.println("(4) Print order invoice");
			System.out.println("(5) Place an order");
			System.out.println("(6) Add a table");
			System.out.println("(7) Remove a table");
			System.out.println("(8) Print tableList");
			System.out.println("(9) Exit");
			System.out.print("Enter the number of your choice: ");
			c = Integer.parseInt(s.next());
		}
		saveTableList();
	}
	
	public static void initializeTableList() {
		String tableFile = "table";
		
		try {
			FileInputStream fiStream = new FileInputStream(tableFile);
			BufferedInputStream biStream = new BufferedInputStream(fiStream);
			ObjectInputStream diStream = new ObjectInputStream(biStream);
			tableList = (TableList)diStream.readObject();
			Table.setCount(diStream.readInt());
			diStream.close();
			
		}
		catch (ClassNotFoundException e) {
			System.out.println("Cannot find the class for the object");
			System.exit(0);
		}
		catch (EOFException e) {
			System.out.println("End of File!");
		}
		catch (FileNotFoundException e) {
			try {
				FileOutputStream foStream = new FileOutputStream(tableFile);
				foStream.close();
			}
			catch (FileNotFoundException er) {
				System.out.println("IOError: File not found!" + tableFile);
				System.exit(0);
			}
			catch (IOException er) {
				System.out.println("File IO Error!" + e.getMessage());
				System.exit(0);
			}
//			System.out.println("IOError: File not found!" + tableFile);
//			System.exit(0);
		}
		catch (IOException e) {
			System.out.println("File IO Error!" + e.getMessage());
			System.exit(0);
		}
	}
	
	public static void saveTableList() {
		String tableFile = "table";
		
		try {
			FileOutputStream foStream = new FileOutputStream(tableFile);
			BufferedOutputStream boStream = new BufferedOutputStream(foStream);
			ObjectOutputStream doStream = new ObjectOutputStream(boStream);
			doStream.writeObject(tableList);
			doStream.writeInt(Table.getCount());
			doStream.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("IOError: File not found!" + tableFile);
			System.exit(0);
		}
		catch (IOException e) {
			System.out.println("File IO Error!" + e.getMessage());
			System.exit(0);
		}
	}
}