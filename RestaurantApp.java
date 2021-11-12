import java.io.*;
import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.Scanner;

enum TableStatus{OCCUPIED, VACANT, RESERVED;}

public class RestaurantApp {

	private static TableList tableList = new TableList();
	private static StaffList staffList = new StaffList();
	private static Menu menu = new Menu();
	private static Hashtable<Integer, Order> currentOrders = new Hashtable<Integer, Order>();
	private static Report report = new Report();
	
	public static void main(String args[]) {
		// Load data from files/Create files to store data
		initializeData();
		
		Scanner s = new Scanner(System.in);
		System.out.println("(1) Configure Restaurant");
		System.out.println("(2) Reservations");
		System.out.println("(3) Order");
		System.out.println("(5) Exit");
		System.out.print("Choose an option: ");
		int c = s.nextInt();
		
		while (1 <= c && c <= 3) {
			switch(c) {
				case 1:
					subMenuOne();
					break;
				case 2:
					subMenuTwo();
					break;
				case 3:
					subMenuThree();
					break;
				default:
					System.out.println("Invalid input!");
					break;
			}
			
			System.out.println("(1) Configure Restaurant");
			System.out.println("(2) Reservations");
			System.out.println("(3) Order");
			System.out.println("(5) Exit");
			System.out.print("Choose an option: ");
			c = s.nextInt();
		}
		s.close();
		
		// Save data to file
		saveData();
	}

	public static void subMenuOne(){
		Scanner scanner = new Scanner(System.in);
		int choice;
		System.out.println("Choose one of the following options to configure the restaurant!");
		System.out.println("(1) Add Menu Item");
		System.out.println("(2) Remove Menu Item");
		System.out.println("(3) Add Promo Menu Item");
		System.out.println("(4) Remove Promo Menu Item");
		System.out.println("(5) Print Menu");
		System.out.println("(6) Add a table");
		System.out.println("(7) Remove a table");
		System.out.println("(8) Print tableList");
		System.out.println("(9) Edit individual menu items/ promotional set packages");
		System.out.println("(10) Exit Submenu");
		choice = scanner.nextInt();
		do{
			switch(choice){
				case 1:
					System.out.print("Enter the name of the new dish: ");
					String name = scanner.next();
					System.out.print("Enter the description of the new dish: ");
					String description = scanner.next();
					System.out.print("Enter the price of the new dish: ");
					double price = scanner.nextDouble();
					System.out.print("Enter the type of the new dish (starter/ main course/ dessert): ");
					String type = scanner.next();
					menu.addMenuItem(new MenuItem(name, description, price, type));
					System.out.println("Item added!");
					break;
				case 2:
					System.out.println("To remove a menu item, please enter its unique itemID. The menu is printed for reference.");
					menu.printMenu();
					int index = scanner.nextInt();
					menu.removeItem(index);
					break;
				case 3:
					System.out.print("Enter the name of the new promo package: ");
					name = scanner.next();
					System.out.print("Enter the description of the new promo package: ");
					description = scanner.next();
					System.out.print("Enter the price of the new promo package: ");
					price = scanner.nextDouble();
					PromotionalSetPackage newPromo = new PromotionalSetPackage(price, description, name);
					System.out.println("Please choose the Menu Items that you wish to add to the promo.");
					System.out.println("To do so, you can key in the item's itemID and the quantity!");
					while(true){
						System.out.println("Enter the itemID of the item you wish to add to the promo (-1 to stop adding): ");
						int itemID = scanner.nextInt();
						if(itemID == -1){
							break;
						}
						while(itemID > 199){
							System.out.println("Please enter a valid itemID!");
							itemID = scanner.nextInt();
						}
						System.out.println("Enter the quantity of the item you wish to add to the promo: ");
						int quantity = scanner.nextInt();
						OrderableItems itemToAdd = menu.getItem(itemID);
						if(itemToAdd == null){
							System.out.println("Please enter a valid itemID!");
							continue;
						} else newPromo.addItem((MenuItem) itemToAdd, quantity);
					}
					menu.addPromotionalSetPackage(newPromo);
					System.out.println("Item added!");
					break;
				case 4:
					System.out.println("To remove a promo package, please enter its unique itemID. The menu is printed for reference.");
					menu.printMenu();
					index = scanner.nextInt();
					menu.removeItem(index);
					break;
				case 5:
					menu.printMenu();
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
					editIndividualItems();
					break;
				case 10:
					break;
				default:
					System.out.println("Invalid input!");
					break;
			}
			System.out.println("(1) Add Menu Item");
			System.out.println("(2) Remove Menu Item");
			System.out.println("(3) Add Promo Menu Item");
			System.out.println("(4) Remove Promo Menu Item");
			System.out.println("(5) Print Menu");
			System.out.println("(6) Add a table");
			System.out.println("(7) Remove a table");
			System.out.println("(8) Print tableList");
			System.out.println("(9) Edit individual menu items/ promotional set packages");
			System.out.println("(10) Exit Submenu");
			choice = scanner.nextInt();
		} while(choice != 10);
		scanner.close();
		System.out.println("Returning to main menu...");
		System.out.println("=========================");
	}

	public static void editIndividualItems(){
		System.out.println("Editing individual items submenu");
		System.out.println("(1) Edit Menu Item");
		System.out.println("(2) Edit Promotional Set Item");
		System.out.println("(3) Return to restaurant configuration menu");
		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();
		do{
			switch(choice){
				case 1:
					System.out.println("To edit a menu item, please enter its unique itemID. The menu is printed for reference.");
					menu.printMenu();
					int index = scanner.nextInt();
					while(index > 199){
						System.out.println("Please enter a valid itemID for Menu Items!");
						index = scanner.nextInt();
					}
					System.out.println("Select what you want to edit for this item: ");
					System.out.println("(1) Name");
					System.out.println("(2) Description");
					System.out.println("(3) Price");
					System.out.println("(4) Type");
					int editChoice = scanner.nextInt();
					MenuItem itemToEdit = (MenuItem) menu.getItem(index);
					switch(editChoice){
						case 1:
							System.out.print("Enter the new name: ");
							String newName = scanner.next();
							itemToEdit.setName(newName);
							break;
						case 2:
							System.out.print("Enter the new description: ");
							String newDescription = scanner.next();
							itemToEdit.setDescription(newDescription);
							break;
						case 3:
							System.out.print("Enter the new price: ");
							double newPrice = scanner.nextDouble();
							itemToEdit.setPrice(newPrice);
							break;
						case 4:
							System.out.println("Select the new type: ");
							System.out.println("(1) Drink");
							System.out.println("(2) Starter");
							System.out.println("(3) Main Course");
							System.out.println("(4) Dessert");
							String newType = scanner.next();
							itemToEdit.setType(newType);
							break;
						default:
							System.out.println("Invalid input!");
							break;
					}
					break;
				case 2:
					System.out.println("To edit a promotional set item, please enter its unique itemID. The menu is printed for reference.");
					menu.printMenu();
					index = scanner.nextInt();
					while(index < 200){
						System.out.println("Please enter a valid itemID for promo items!");
						index = scanner.nextInt();
					}
					System.out.println("Select what you want to edit for this item: ");
					System.out.println("(1) Name");
					System.out.println("(2) Description");
					System.out.println("(3) Price");
					System.out.println("(4) Add new item");
					System.out.println("(5) Remove menu item");
					editChoice = scanner.nextInt();
					PromotionalSetPackage promoToEdit = (PromotionalSetPackage) menu.getItem(index);
					switch(editChoice){
						case 1:
							System.out.print("Enter the new name: ");
							String newName = scanner.next();
							promoToEdit.setName(newName);
							break;
						case 2:
							System.out.print("Enter the new description: ");
							String newDescription = scanner.next();
							promoToEdit.setDescription(newDescription);
							break;
						case 3:
							System.out.print("Enter the new price: ");
							double newPrice = scanner.nextDouble();
							promoToEdit.setPrice(newPrice);
							break;
						case 4:
							System.out.println("Select the itemID of the menu item to add (Menu printed for reference): ");
							menu.printMenu();
							int newItemID = scanner.nextInt();
							while(newItemID > 199){
								System.out.println("Please enter a valid itemID for Menu Items!");
								newItemID = scanner.nextInt();
							}
							System.out.println("Enter the quantity for this item: ");
							int newQuantity = scanner.nextInt();
							MenuItem menuItemToAdd = (MenuItem) menu.getItem(newItemID);
							if(menuItemToAdd == null){
								System.out.println("Invalid itemID!");
							}
							else{
								promoToEdit.addItem(menuItemToAdd, newQuantity);
							}
							break;
						case 5:
							System.out.println("Select the itemID of the menu item to remove (Menu printed for reference): ");
							menu.printMenu();
							newItemID = scanner.nextInt();
							while(newItemID > 199){
								System.out.println("Please enter a valid itemID for Menu Items!");
								newItemID = scanner.nextInt();
							}
							MenuItem menuItemToRemove = (MenuItem) menu.getItem(newItemID);
							if(menuItemToRemove == null){
								System.out.println("Invalid itemID!");
							}
							else{
								System.out.println("How many of quantities of the item do you wish to remove?");
								newQuantity = scanner.nextInt();
								promoToEdit.removeItem(menuItemToRemove, newQuantity);
							}
							break;
						default:
							System.out.println("Invalid input!");
							break;
					}
					break;
				case 3:
					break;
			}
			System.out.println("Editing individual items submenu");
			System.out.println("(1) Edit Menu Item");
			System.out.println("(2) Edit Promotional Set Item");
			System.out.println("(3) Return to restaurant configuration menu");
			choice = scanner.nextInt();
		} while (choice != 3);
		scanner.close();
		System.out.println("Returning to restaurant configuration submenu");
	}
	
	public static void subMenuTwo() {
		Scanner s = new Scanner(System.in);
		System.out.println("(1) Create reservation booking");
		System.out.println("(2) Check/Remove reservation booking");
		System.out.println("(3) Exit");
		System.out.print("Choose an option: ");
		int c = s.nextInt();
		
		while (1 <= c && c <= 2) {
			switch(c) {
				case 1:
					System.out.print("Enter date (YYYY-MM-DD): ");
					String date = s.next();
					System.out.print("Enter time (hh:mm): ");
					String time = s.next();
					time = time + ":00";
					System.out.print("Enter number of pax: ");
					int paxNumber = s.nextInt();
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
				 	int d = s.nextInt();
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
				 	 		break;
				 	 	default:
							break;
				 	}
				 	break;
				default:
					System.out.println("Invalid input!");
					break;
			}
			
			System.out.println("(1) Create reservation booking");
			System.out.println("(2) Check/Remove reservation booking");
			System.out.println("(3) Exit");
			System.out.print("Choose an option: ");
			c = s.nextInt();
		}
	}
	
	public static void subMenuThree() {
		Scanner s = new Scanner(System.in);
		System.out.println("(1) Check table availability");
		System.out.println("(2) Place an order");
		System.out.println("(3) Print order invoice");
		System.out.println("(4) Exit");
		System.out.print("Choose an option: ");
		int c = s.nextInt();
		
		while (1 <= c && c <= 3) {
			switch(c) {
				case 1:
					tableList.checkTableAvailability();
					break;
				 case 2:
					 createOrder();
				 	break;
				 case 3:
					 printInvoice();
					 break;
				default:
					System.out.println("Invalid input!");
					break;
			}
			
			System.out.println("(1) Check table availability");
			System.out.println("(2) Place an order");
			System.out.println("(3) Print order invoice");
			System.out.println("(4) Exit");
			System.out.print("Choose an option: ");
			c = s.nextInt();
		}
	}
	public static void createOrder(){
		Scanner s = new Scanner(System.in);
		
		// Create an order object
		System.out.println("Enter Staff ID");
		int staffID = s.nextInt();
		System.out.println("Member?");
		boolean isMember = s.nextBoolean();
		System.out.println("Does the customer have a reservation? Y/N");
		boolean reserved = s.next().equalsIgnoreCase("Y")? true:false;
		int tableID;
		
		if (reserved)
		{
			Reservation rez;
			System.out.print("Enter customer's contact no.: ");
			String contact = s.next();
			while ((rez = tableList.findRez(contact)) == null) {
				System.out.print("Cannot find the reservation. Please enter a valid customer's contact no.: ");
				contact = s.next();
			}
			tableID = rez.getTableNumber();
		}
		else
		{
			tableList.checkTableAvailability();
			System.out.print("Assign to table ID: ");
			tableID = s.nextInt();
			while (tableList.getTableList().get(tableID).getStatus() != TableStatus.VACANT)
				System.out.println("Please select a vacant table!");
				tableList.checkTableAvailability();
				System.out.print("Assign to table ID: ");
				tableID = s.nextInt();
		}
		Order order = new Order(staffID, tableID, isMember, staffList.getStaff(staffID).getName());
		currentOrders.put(tableID, order);
		
		// Set TableStatus to OCCUPIED after order
		Table table = tableList.getTableList().get(tableID);
		table.setStatus(TableStatus.OCCUPIED);
		
		// Add/Remove items from an order object
		System.out.println("ADD/REMOVE ITEMS FROM ORDER");
		System.out.println("(1) Add Item");
		System.out.println("(2) Remove Item");
		System.out.println("(3) Print Menu");
		System.out.println("(4) Done! Create Order");
		System.out.print("Enter a choice: ");
		int n = s.nextInt();
		while (1 <= n && n <= 3) {
			switch(n) {
				case 1:
					System.out.print("Enter ItemID to add: ");
					int itemID = s.nextInt();
					OrderableItems orderableItem = menu.getItem(itemID);
					System.out.println("Enter Quantity: ");
					int quantity = s.nextInt();
					
					order.addItem(orderableItem, quantity);
					// System.out.println("Item Added!");
					break;
				case 2:
					System.out.println("Enter ItemID to remove: ");
					itemID = s.nextInt();
					orderableItem = menu.getItem(itemID);
					System.out.println("Enter Quantity: ");
					quantity = s.nextInt();
					
					order.removeItem(orderableItem, quantity);
					// System.out.println("Item Removed!");
					break;
				case 3:
					menu.printMenu();
					break;
				default:
					System.out.println("Invalid input!");
					break;
			}
			System.out.println("(1) Add Item");
			System.out.println("(2) Remove Item");
			System.out.println("(3) Print Menu");
			System.out.println("(4) Done! Create Order");
			System.out.print("Enter a choice: ");
			n = s.nextInt();
		}
		System.out.println("Order Created!");
	}
	
	public static void printInvoice() {
		Scanner s = new Scanner(System.in);
		
		System.out.print("Enter TableID: ");
		int tableID = s.nextInt();
		while (tableList.getTableList().get(tableID).getStatus() != TableStatus.OCCUPIED) {
			System.out.println("Please input an OCCUPIED table!");
			System.out.print("Enter TableID: ");
			tableID = s.nextInt();
		}
		Order order = currentOrders.get(tableID);
		
		// Print invoice
		order.printOrderInvoice();
		
		// Add the order to the report object
		report.addOrder(order);
		
		// Change the TableStatus to VACANT
		Table table = tableList.getTableList().get(tableID);
        table.setStatus(TableStatus.VACANT);
	}
	
	public static void initializeData() {
		String tableFile = "tables";
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
			Staff.setCount(diStreamStaff.readInt());
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
				foStream = new FileOutputStream(menuFile);
				foStream.close();
				foStream = new FileOutputStream(staffFile);
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
	
	public static void saveData() {
		String tableFile = "tables";
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
			doStreamStaff.writeInt(Staff.getCount());
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
