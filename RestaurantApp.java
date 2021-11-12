import java.io.*;
import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.Scanner;

enum TableStatus{OCCUPIED, VACANT, RESERVED;}

/* TODO - Create the staffList for this class */

public class RestaurantApp {

	private static Hashtable<Integer, Order> currentOrders = new Hashtable<Integer, Order>();
	private static TableList tableList = new TableList();
	private static StaffList staffList = new StaffList();
	private static Menu menu = new Menu();
	private static Report report = new Report();
	
	public static void main(String args[]) {
		
		Scanner s = new Scanner(System.in);
		
		initializeTableList();
		tableList.print();
		System.out.println("(1) Configure restaurant");
		// ADD AS SUB MENU
		// System.out.println("(9) Print Menu");
		// System.out.println("(10) Add Menu Item");
		// System.out.println("(11) Remove Menu Item");
		// System.out.println("(12) Add Promo Menu Item");
		// System.out.println("(13) Remove Promo Menu Item");
		// System.out.println("(6) Add a table");
		// System.out.println("(7) Remove a table");
		// System.out.println("(8) Print tableList");
		System.out.println("(2) Reservations");
		// System.out.println("(1) Create reservation booking");
		// System.out.println("(2) Check/Remove reservation booking");
		System.out.println("(3) Order");
		// System.out.println("(3) Check table availability");
		// System.out.println("(5) Place an order");
		// System.out.println("(4) Print order invoice");
		System.out.println("(9) Exit");
		System.out.print("Enter the number of your choice: ");
		int c = Integer.parseInt(s.next());
		
		while (1 <= c && c <= 9) {
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
					System.out.println("Enter TableID?");
					int tableID = s.nextInt();
					Order order = currentOrders.get(tableID);
					order.printOrderInvoice();
					Table table = tableList.getTableList().get(tableID);
					report.addOrder(order);
                    table.setStatus(TableStatus.VACANT);
					break;
				case 5: 
					createOrder();
					break;
				case 6:
					tableList.addTable();
					break;
				case 7:
					tableList.removeTable();
					break;
				case 8:
					tableList.print();
					break;
				case 9:
					// menu.addMenuItem(new MenuItem("test1", "just a trial object", 9.99, "dessert"));
					// System.out.println("Item added!");
					menu.printMenu();
					// menu.removeItem(0);

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
		s.close();
	}

	public static void createOrder(){
		Scanner s = new Scanner(System.in);
		System.out.println("Enter Staff ID");
		int staffID = s.nextInt();
		System.out.println("Member?");
		boolean isMember = s.nextBoolean();
		System.out.println("Do you have a reservation? Y/N");
		boolean reserved = s.next().equalsIgnoreCase("Y")? true:false;
		int tableID;
		Order order;
		if(reserved)
		{
			String contact = s.next();
			Reservation rez = tableList.findRez(contact);
			tableID = rez.getTableNumber();
			order = new Order(staffID,tableID,isMember, staffList.getStaff(staffID).getName());
		}
		else
		{
			tableList.checkTableAvailability();
			System.out.println("Enter Table ID?");
			tableID = s.nextInt();
			order = new Order(staffID, tableID, isMember, staffList.getStaff(staffID).getName());
		}
		currentOrders.put(tableID, order);
		Table table = tableList.getTableList().get(tableID);
								table.setStatus(TableStatus.OCCUPIED);
		System.out.println("(1) Add Item");
		System.out.println("(2) Remove Item");
		System.out.println("(3) Exit");
		int n = s.nextInt();
		while (1 <= n && n <= 3) {
			switch(n) {
				case 1:
					System.out.println("Enter ItemID to add");
					int itemID = s.nextInt();
					OrderableItems orderableItem = menu.getItem(itemID);
					System.out.println("Enter Quantity");
					int quantity = s.nextInt();
					
					order.addItem(orderableItem, quantity);
					System.out.println("Next Choice");
					n = s.nextInt();
					break;
				case 2:
					System.out.println("Enter ItemID to remove");
					itemID = s.nextInt();
					orderableItem = menu.getItem(itemID);
					System.out.println("Enter Quantity");
					quantity = s.nextInt();
					
					order.removeItem(orderableItem,quantity);
					System.out.println("Next Choice");
					n = s.nextInt();
					break;
				case 3:
					System.out.println("Order Created!");
					n = -1;
					break;
				case 4:
					menu.printMenu();
			}
		}
		s.close();
	}
	
	public static void initializeTableList() {
		String tableFile = "table";
		String menuFile = "menu";
		String staffFile = "staff";

		try {
			/* FOR TABLES */
			FileInputStream fiStream = new FileInputStream(tableFile);
			BufferedInputStream biStream = new BufferedInputStream(fiStream);
			ObjectInputStream diStream = new ObjectInputStream(biStream);
			tableList = (TableList)diStream.readObject();
			Table.setCount(diStream.readInt());
			diStream.close();

			/* FOR MENU */
			FileInputStream fiStreamMenu = new FileInputStream(menuFile);
			BufferedInputStream biStreamMenu = new BufferedInputStream(fiStreamMenu);
			ObjectInputStream diStreamMenu = new ObjectInputStream(biStreamMenu);
			menu = (Menu)diStreamMenu.readObject();
			diStreamMenu.close();

			/* FOR STAFF */
			FileInputStream fiStreamStaff = new FileInputStream(staffFile);
			BufferedInputStream biStreamStaff = new BufferedInputStream(fiStreamStaff);
			ObjectInputStream diStreamStaff = new ObjectInputStream(biStreamStaff);
			staffList = (StaffList)diStreamStaff.readObject();
			diStreamStaff.close();
			
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
		String menuFile = "menu";
		String staffFile = "staff";

		try {
			/* FOR TABLES */
			FileOutputStream foStream = new FileOutputStream(tableFile);
			BufferedOutputStream boStream = new BufferedOutputStream(foStream);
			ObjectOutputStream doStream = new ObjectOutputStream(boStream);
			doStream.writeObject(tableList);
			doStream.writeInt(Table.getCount());
			doStream.close();

			/* FOR MENU */
			FileOutputStream foStreamMenu = new FileOutputStream(menuFile);
			BufferedOutputStream boStreamMenu = new BufferedOutputStream(foStreamMenu);
			ObjectOutputStream doStreamMenu = new ObjectOutputStream(boStreamMenu);
			doStreamMenu.writeObject(menu);
			doStreamMenu.close();

			/* FOR STAFF*/
			FileOutputStream foStreamStaff = new FileOutputStream(staffFile);
			BufferedOutputStream boStreamStaff = new BufferedOutputStream(foStreamStaff);
			ObjectOutputStream doStreamStaff = new ObjectOutputStream(boStreamStaff);
			doStreamStaff.writeObject(staffList);
			doStreamStaff.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("IOError: File not found!" + tableFile);
			System.exit(0);
		}
		catch (IOException e) {
			System.out.println("File IO Error! " + e.getMessage());
			System.exit(0);
		}
	}
}
