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
					subMenuOne();
				// case 2:
				// 	tableList.updateAllRezs();
				// 	System.out.println("(1) Display all current reservations");
				// 	System.out.println("(2) Check/Remove a reservation");
				// 	int d = Integer.parseInt(s.next());
				// 	switch(d) {
				// 	 	case 1:
				// 	 		tableList.printAllRezs();
				// 	 		break;
				// 	 	case 2:
				// 	 		System.out.print("Retrieve reservation for customer of contact no.: ");
				// 	 		contact = s.next();
				// 	 		Reservation rez = tableList.findRez(contact);
				// 	 		if (rez == null)
				// 	 			System.out.println("No reservation created for that contact no.");
				// 	 		else {
				// 	 			rez.print();
				// 	 			System.out.println("Do you want to remove this reservation? (Y/N)");
				// 	 			String choice = s.next();
				// 	 			if (choice.equalsIgnoreCase("Y")) {
				// 	 				Table table = tableList.getTableList().get(rez.getTableNumber());
				// 		 			if (rez.getDateTime().compareTo(LocalDateTime.now().plusHours(1)) <= 0)
				// 						if (table.getStatus() == TableStatus.RESERVED)
				// 							table.setStatus(TableStatus.VACANT);
				// 		 			tableList.removeRez(rez);
				// 	 			}
				// 	 		}
				// 	}
				// 	break;
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
					System.out.printf("Input the size of the new table: ");
					int size = Integer.parseInt(scanner.next());
					tableList.addTable(size);
					break;
				case 7:
					System.out.printf("Input the ID of the table to be removed: ");
					int rID = Integer.parseInt(scanner.next());
					tableList.removeTable(rID);
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
