/**
 * Represents the main driver class for the RestaurantApp containing 
 * essential classes required to ensure smooth flow in the console app.
 * @author  SS10G5
 * @version 12.0
 * @since   2021-10-25
*/

import java.io.*;
import java.time.Clock;
import java.time.Duration;
import java.time.*;
import java.time.format.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

enum TableStatus{OCCUPIED, VACANT, RESERVED;}

public class RestaurantApp {

	private static Hashtable<Integer, Order> currentOrders = new Hashtable<Integer, Order>();
	private static TableList tableList = new TableList();
	private static StaffList staffList = new StaffList();
	private static MemberList memberList = new MemberList();
	private static Menu menu = new Menu();
	private static Report report = new Report();
	
	public static Clock clock = Clock.offset(Clock.systemDefaultZone(), Duration.ofHours(8));
	public static Clock clockbf = Clock.offset(Clock.systemDefaultZone(), Duration.ofHours(31));
	public static Clock clockat = Clock.offset(Clock.systemDefaultZone(), Duration.ofMinutes(1935));
	public static Scanner s = new Scanner(System.in);

	
	/** 
	 * @param args[]
	 */
	public static void main(String args[]) {
		// Load data from files/Create files to store data
		initializeData();
		
		int c;
		do {
			System.out.println("(1) Configure Restaurant");
			System.out.println("(2) Reservations");
			System.out.println("(3) Order");
			System.out.println("(4) Print Revenue Report");
			System.out.println("(5) Test");
			System.out.println("(6) Exit");
			System.out.print("Choose an option: ");
			c = Integer.parseInt(s.nextLine());

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
				case 4:
					printRevenueReport();
					break;
				case 5:
					testSubMenu();
					break;
				case 6:
					break;
				default:
					System.out.println("Invalid input!");
					break;
			}
		} while (c != 6);

		s.close();
		
		// Save data to file
		saveData();
	}

	// Sub-menu 1, and associated subfunctions
	private static void subMenuOne(){
		//Scanner scanner = new Scanner(System.in);
		int choice;
		do{
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
			System.out.println("(10) Staff Submenu");
			System.out.println("(11) Member Submenu");
			System.out.println("(12) To Main Menu");
			choice = Integer.parseInt(s.nextLine());
			switch(choice){
				case 1:
					System.out.print("Enter the name of the new dish: ");
					String name = s.nextLine();
					System.out.print("Enter the description of the new dish: ");
					String description = s.nextLine();
					System.out.print("Enter the price of the new dish: ");
					double price = Double.parseDouble(s.nextLine());
					System.out.print("Enter the type of the new dish (starter/ main course/ dessert): ");
					String type = s.nextLine();
					menu.addMenuItem(new MenuItem(name, description, price, type));
					System.out.println("Item added!");
					break;
				case 2:
					System.out.println("To remove a menu item, please enter its unique itemID. The menu is printed for reference.");
					menu.printMenu();
					int index = Integer.parseInt(s.nextLine());
					menu.removeItem(index);
					break;
				case 3:
					System.out.print("Enter the name of the new promo package: ");
					name = s.nextLine();
					System.out.print("Enter the description of the new promo package: ");
					description = s.nextLine();
					System.out.print("Enter the price of the new promo package: ");
					price = Double.parseDouble(s.nextLine());
					PromotionalSetPackage newPromo = new PromotionalSetPackage(price, description, name);
					System.out.println("Please choose the Menu Items that you wish to add to the promo.");
					System.out.println("To do so, you can key in the item's itemID and the quantity!");
					while(true){
						System.out.println("Enter the itemID of the item you wish to add to the promo (-1 to stop adding): ");
						int itemID = Integer.parseInt(s.nextLine());
						if(itemID == -1){
							break;
						}
						while(itemID > 199){
							System.out.println("Please enter a valid itemID!");
							itemID = Integer.parseInt(s.nextLine());
						}
						System.out.println("Enter the quantity of the item you wish to add to the promo: ");
						int quantity = Integer.parseInt(s.nextLine());;
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
					index = Integer.parseInt(s.nextLine());
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
					staffSubMenu();
					break;
				case 11:
					memberSubMenu();
					break;
				case 12:
					break;
				default:
					System.out.println("Invalid input!");
					break;
			}
		} while(choice != 12);
		System.out.println("Returning to main menu...");
		System.out.println("=========================");
	}

	private static void editIndividualItems(){
		int choice;
		do{
			System.out.println("Editing individual items submenu");
			System.out.println("(1) Edit Menu Item");
			System.out.println("(2) Edit Promotional Set Item");
			System.out.println("(3) Return to restaurant configuration menu");
			choice = Integer.parseInt(s.nextLine());
			switch(choice){
				case 1:
					System.out.println("To edit a menu item, please enter its unique itemID. The menu is printed for reference.");
					menu.printMenu();
					int index = Integer.parseInt(s.nextLine());;
					while(index > 199){
						System.out.println("Please enter a valid itemID for Menu Items!");
						index = Integer.parseInt(s.nextLine());;
					}
					System.out.println("Select what you want to edit for this item: ");
					System.out.println("(1) Name");
					System.out.println("(2) Description");
					System.out.println("(3) Price");
					System.out.println("(4) Type");
					System.out.print("Choose an option: ");
					int editChoice = Integer.parseInt(s.nextLine());
					MenuItem itemToEdit = (MenuItem) menu.getItem(index);
					switch(editChoice){
						case 1:
							System.out.print("Enter the new name: ");
							String newName = s.nextLine();
							itemToEdit.setName(newName);
							break;
						case 2:
							System.out.print("Enter the new description: ");
							String newDescription = s.nextLine();
							itemToEdit.setDescription(newDescription);
							break;
						case 3:
							System.out.print("Enter the new price: ");
							double newPrice = Double.parseDouble(s.nextLine());
							itemToEdit.setPrice(newPrice);
							break;
						case 4:
							System.out.println("Select the new type: ");
							System.out.println("(1) Drink");
							System.out.println("(2) Starter");
							System.out.println("(3) Main Course");
							System.out.println("(4) Dessert");
							System.out.print("Enter the type: ");
							String newType = s.next();
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
					index = Integer.parseInt(s.nextLine());
					while(index < 200){
						System.out.println("Please enter a valid itemID for promo items!");
						index = Integer.parseInt(s.nextLine());
					}
					if(menu.getItem(index) == null){
						System.out.println("This promo pckage doesn't exist");
						continue;
					}
					System.out.println("Select what you want to edit for this item: ");
					System.out.println("(1) Name");
					System.out.println("(2) Description");
					System.out.println("(3) Price");
					System.out.println("(4) Add new item");
					System.out.println("(5) Remove menu item");
					System.out.print("Choose an option: ");
					editChoice = Integer.parseInt(s.nextLine());
					PromotionalSetPackage promoToEdit = (PromotionalSetPackage) menu.getItem(index);
					switch(editChoice){
						case 1:
							System.out.print("Enter the new name: ");
							String newName = s.nextLine();
							promoToEdit.setName(newName);
							break;
						case 2:
							System.out.print("Enter the new description: ");
							String newDescription = s.nextLine();
							promoToEdit.setDescription(newDescription);
							break;
						case 3:
							System.out.print("Enter the new price: ");
							double newPrice = Double.parseDouble(s.nextLine());
							promoToEdit.setPrice(newPrice);
							break;
						case 4:
							System.out.println("Select the itemID of the menu item to add (Menu printed for reference): ");
							menu.printMenu();
							int newItemID = Integer.parseInt(s.nextLine());
							while(newItemID > 199){
								System.out.println("Please enter a valid itemID for Menu Items!");
								newItemID = Integer.parseInt(s.nextLine());
							}
							System.out.println("Enter the quantity for this item: ");
							int newQuantity = Integer.parseInt(s.nextLine());;
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
							newItemID = Integer.parseInt(s.nextLine());
							while(newItemID > 199){
								System.out.println("Please enter a valid itemID for Menu Items!");
								newItemID = Integer.parseInt(s.nextLine());
							}
							MenuItem menuItemToRemove = (MenuItem) menu.getItem(newItemID);
							if(menuItemToRemove == null){
								System.out.println("Invalid itemID!");
							}
							else{
								System.out.println("How many of quantities of the item do you wish to remove?");
								newQuantity = Integer.parseInt(s.nextLine());
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
		} while (choice != 3);
		System.out.println("Returning to restaurant configuration submenu");
	}
	
	private static void memberSubMenu(){
		int choice;
		do{
			System.out.println("Choose one of the following options to configure the restaurant!");
			System.out.println("(1) Add Member");
			System.out.println("(2) Remove Member");
			System.out.println("(3) Change Name");
			System.out.println("(4) Change Number");
			System.out.println("(5) Print list of members");
			System.out.println("(6) Return to previous menu");
			choice = Integer.parseInt(s.nextLine());
			switch(choice){
				case 1:
					System.out.print("Enter the name of the new customer member: ");
					String name = s.nextLine();
					System.out.print("Enter the number of the new customer member: ");
					String number = s.nextLine();
					memberList.addMember(name, number);
					System.out.println("Member enrolled!");
					break;
				case 2:
					System.out.println("To remove a customer as a member, please enter their unique memberID");
					int memberID = Integer.parseInt(s.nextLine());
					memberList.removeMember(memberID);
					break;
				case 3:
					System.out.print("Enter the new name of the customer member : ");
					name = s.nextLine();
					System.out.print("Enter the number of the customer member : ");
				    number = s.nextLine();
					System.out.print("Enter the memberID of the customer member: ");
					memberID = Integer.parseInt(s.nextLine());
					memberList.changeName(memberID, number, name);
					break;
				case 4:
					System.out.print("Enter the name of the customer member : ");
					name = s.nextLine();
					System.out.print("Enter the new number of the customer member : ");
					number = s.nextLine();
					System.out.print("Enter the memberID of the customer member: ");
					memberID = Integer.parseInt(s.nextLine());
					memberList.changeNumber(memberID, name, number);
					break;
				case 5:
					memberList.printMemberList();
				case 6:
					break;
				default:
					System.out.println("Invalid input!");
			}
		}while(choice != 6);
		System.out.println("Returning to main menu...");
	}
			
	private static void staffSubMenu(){
		int choice;
		do{
			System.out.println("Choose one of the following options to configure the restaurant!");
			System.out.println("(1) Add Staff Member");
			System.out.println("(2) Remove Staff Member");
			System.out.println("(3) Print Staff List");
			System.out.println("(4) Exit Submenu");
			choice = Integer.parseInt(s.nextLine());
			switch(choice){
				case 1:
					System.out.print("Enter the name of the new staff member: ");
					String name = s.nextLine();
					System.out.print("Enter the gender of the new staff member(M/F): ");
					char gender = s.nextLine().charAt(0);
					System.out.print("Enter the job title of the new staff member: ");
					String title = s.nextLine();
					staffList.addStaff(name, gender, title);
					System.out.println("Staff enrolled!");
					break;
				case 2:
					System.out.println("To remove a staff member, please enter their unique staffID. The staff list is printed for reference");
					staffList.printStaffList();
					int index = Integer.parseInt(s.nextLine());
					staffList.removeStaff(index);
					break;
				case 3:
					staffList.printStaffList();
					break;
				case 4:
					break;
				default:
					System.out.println("Invalid input!");
			}
		}while(choice != 4);
		System.out.println("Returning to restaurant configuration submenu...");
	}
	
	// Sub-menu 2
	private static void subMenuTwo() {
		System.out.println("(1) Create reservation booking");
		System.out.println("(2) Check/Remove reservation booking");
		System.out.println("(3) To Main Menu");
		System.out.print("Choose an option: ");
		int c = Integer.parseInt(s.nextLine());
		
		while (c != 3) {
			switch(c) {
				case 1:
					// Input date
					boolean correctInput = false;
					String date = null;
					while (!correctInput) {
						System.out.print("Enter date (YYYY-MM-DD): ");
						date = s.nextLine();
						try {
							correctInput = true;
							LocalDate.parse(date);
						}
						catch(DateTimeParseException e) {
							System.out.println("Please enter the correct date format!");
							correctInput = false;
						}
					}
					// Input time
					correctInput = false;
					String time = null;
					while (!correctInput) {
						System.out.print("Enter time (hh:mm): ");
						time = s.nextLine();
						time = time + ":00";
						try {
							correctInput = true;
							LocalTime.parse(time);
						}
						catch(DateTimeParseException e) {
							System.out.println("Please enter the correct time format!");
							correctInput = false;
						}
					}

					System.out.print("Enter number of pax: ");
					int paxNumber = Integer.parseInt(s.nextLine());
					System.out.print("Enter customer's name: ");
					String name = s.nextLine();
					System.out.print("Enter customer's contact no.: ");
					String contact = s.nextLine();
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
				 	int d = Integer.parseInt(s.nextLine());
				 	switch(d) {
				 	 	case 1:
				 	 		tableList.printAllRezs();
				 	 		break;
				 	 	case 2:
				 	 		System.out.print("Retrieve reservation for customer of contact no.: ");
				 	 		contact = s.nextLine();
				 	 		Reservation rez = tableList.findRez(contact);
				 	 		if (rez == null)
				 	 			System.out.println("No reservation created for that contact no.");
				 	 		else {
				 	 			rez.print();
				 	 			System.out.println("Do you want to remove this reservation? (Y/N)");
				 	 			String choice = s.nextLine();
				 	 			if (choice.equalsIgnoreCase("Y")) {
				 	 				// Change the TableStatus from RESERVED to VACANT if reservation is canceled within 1hr before the reserved time
				 		 			if (rez.getDateTime().compareTo(LocalDateTime.now().plusHours(1)) <= 0) {
				 		 				Table table = tableList.getTableList().get(rez.getTableNumber());
				 						if (table.getStatus() == TableStatus.RESERVED)
				 							table.setStatus(TableStatus.VACANT);
				 		 			}
				 		 			tableList.removeRez(rez);
				 		 			System.out.println("Reservation removed!");
				 	 			}
				 	 		}
				 	 		break;
				 	 	default:
							break;
				 	}
				 	break;
				 case 3: 
					 break;
				default:
					System.out.println("Invalid input!");
					break;
			}
			
			System.out.println("(1) Create reservation booking");
			System.out.println("(2) Check/Remove reservation booking");
			System.out.println("(3) To Main Menu");
			System.out.print("Choose an option: ");
			c = Integer.parseInt(s.nextLine());
		}
	}
	
	// Sub-menu 3, and associated subfunctions
	private static void subMenuThree() {		
		int c;
		do {
			System.out.println("(1) Check table availability");
			System.out.println("(2) Place an order");
			System.out.println("(3) Update an order");
			System.out.println("(4) View a current order");
			System.out.println("(5) Print order invoice");
			System.out.println("(6) To Main Menu");
			System.out.print("Choose an option: ");
			c = Integer.parseInt(s.nextLine());
			switch(c) {
				case 1:
					tableList.checkTableAvailability();
					break;
				case 2:
					createOrder();
				 	break;
				case 3:
					updateOrder();
					break;
				case 4:
					viewOrder();
					break;
				case 5:
					printInvoice();
					break;
				case 6:
					break;
				default:
					System.out.println("Invalid input!");
					break;
			}
		} while (c != 6);
	}
	
	private static void createOrder(){
		// Create an order object
		System.out.println("Enter Staff ID");
		int staffID = Integer.parseInt(s.nextLine());
		System.out.println("Member? (Y/N)");
		boolean isMember = s.nextLine().equalsIgnoreCase("Y")? true:false;
		if(isMember)
		{
			System.out.print("Enter customer's number for verification");
			String contact = s.nextLine();
			System.out.print("Enter customer's memberID for verification");
			int memberID = Integer.parseInt(s.nextLine());
			while ( memberList.verifyMember(memberID,contact) == false) {
				System.out.print("Cannot find the reservation. Please enter a valid customer's contact no.: ");
				contact = s.nextLine();
				System.out.print("Please enter a valid customer's memberID: ");
				memberID = Integer.parseInt(s.nextLine());
			}
		}	
		System.out.println("Does the customer have a reservation? (Y/N)");
		boolean reserved = s.nextLine().equalsIgnoreCase("Y")? true:false;
		int tableID;
		
		if (reserved)
		{
			Reservation rez;
			System.out.print("Enter customer's contact no.: ");
			String contact = s.nextLine();
			while ((rez = tableList.findRez(contact)) == null) {
				System.out.print("Cannot find the reservation. Please enter a valid customer's contact no.: ");
				contact = s.nextLine();
			}
			tableID = rez.getTableNumber();
		}
		else
		{
			tableList.checkTableAvailability();
			System.out.print("Assign to table ID: ");
			tableID = Integer.parseInt(s.nextLine());

			// Check if input tableID does not exist or is not occupied
			while (!tableList.exists(tableID) || tableList.getTableList().get(tableID).getStatus() != TableStatus.VACANT) {
				if (!tableList.exists(tableID)) {
					System.out.println("No table with this tableID currently exists!");
				}
				else {
					System.out.println("Please select a vacant table!");
				}
				
				tableList.checkTableAvailability();
				System.out.print("Assign to table ID: ");
				tableID = Integer.parseInt(s.nextLine());
			}
			
		}
		
		Order order = new Order(staffID, tableID, isMember, staffList.getStaff(staffID).getName());
		currentOrders.put(tableID, order);
		
		// Set TableStatus to OCCUPIED after order
		Table table = tableList.getTableList().get(tableID);
		table.setStatus(TableStatus.OCCUPIED);
		
		// Add/Remove items from an order object
		int n;
		do {
			System.out.println("ADD/REMOVE ITEMS FROM ORDER");
			System.out.println("(1) Add Item");
			System.out.println("(2) Remove Item");
			System.out.println("(3) Print Menu");
			System.out.println("(4) Done! Create Order");
			System.out.print("Enter a choice: ");
			n = Integer.parseInt(s.nextLine());
			switch(n) {
				case 1:
					System.out.print("Enter ItemID to add: ");
					int itemID = Integer.parseInt(s.nextLine());
					OrderableItems orderableItem = menu.getItem(itemID);
					System.out.println("Enter Quantity: ");
					int quantity = Integer.parseInt(s.nextLine());
					
					order.addItem(orderableItem, quantity);
					// System.out.println("Item Added!");
					break;
				case 2:
					System.out.println("Enter ItemID to remove: ");
					itemID = Integer.parseInt(s.nextLine());
					orderableItem = menu.getItem(itemID);
					System.out.println("Enter Quantity: ");
					quantity = Integer.parseInt(s.nextLine());
					
					order.removeItem(orderableItem, quantity);
					// System.out.println("Item Removed!");
					break;
				case 3:
					menu.printMenu();
					break;
				case 4:
					// Remove from currentOrders if order is empty
					if (order.getItemSet().isEmpty()) {
						System.out.println("No items have been added! Cancelling this order.");
						currentOrders.remove(tableID);
						table.setStatus(TableStatus.VACANT);
					}
					else {
						System.out.println("Order Created!");
					}
					break;
				default:
					System.out.println("Invalid input!");
					break;
			}
		} while (n != 4);
	}
	
	private static void updateOrder() {
		System.out.print("Enter TableID: ");
		int tableID = Integer.parseInt(s.nextLine());
		
		// Sanity checks
		if (!tableList.exists(tableID)) {
			System.out.println("No table with this tableID currently exists!");
			return;
		}

		Table table = tableList.getTableList().get(tableID);
		if (table.getStatus() != TableStatus.OCCUPIED) {
			System.out.println("No orders currently being served at this table!");
			return;
		}
		
		Order order = currentOrders.get(tableID);
		int n;
		
		do {
			System.out.println("UPDATE ORDER");
			System.out.println("(1) Add Item");
			System.out.println("(2) Remove Item");
			System.out.println("(3) Print Menu");
			System.out.println("(4) Done! Confirm Order Update");
			System.out.print("Enter a choice: ");
			n = Integer.parseInt(s.nextLine());
			switch(n) {
				case 1:
					System.out.print("Enter ItemID to add: ");
					int itemID = Integer.parseInt(s.nextLine());
					OrderableItems orderableItem = menu.getItem(itemID);
					System.out.println("Enter Quantity: ");
					int quantity = Integer.parseInt(s.nextLine());
					
					order.addItem(orderableItem, quantity);
					// System.out.println("Item Added!");
					break;
				case 2:
					System.out.println("Enter ItemID to remove: ");
					itemID = Integer.parseInt(s.nextLine());
					orderableItem = menu.getItem(itemID);
					System.out.println("Enter Quantity: ");
					quantity = Integer.parseInt(s.nextLine());
					
					order.removeItem(orderableItem, quantity);
					// System.out.println("Item Removed!");
					break;
				case 3:
					menu.printMenu();
					break;
				case 4:
					if (order.getItemSet().isEmpty()) {
						System.out.println("Order now contains no items! Cancelling this order.");
						currentOrders.remove(tableID);
						table.setStatus(TableStatus.VACANT);
					}
					else {
						System.out.println("Order updated!");
					}
					break;
				default:
					System.out.println("Invalid input!");
					break;
			}
		} while (n != 4);
	}
	
	private static void viewOrder() {
		System.out.print("Enter TableID: ");
		int tableID = Integer.parseInt(s.nextLine());
		
		// Sanity checks
		if (!tableList.exists(tableID)) {
			System.out.println("No table with this tableID currently exists!");
			return;
		}
		
		if (tableList.getTableList().get(tableID).getStatus() != TableStatus.OCCUPIED) {
			System.out.println("No orders currently being served at this table!");
			return;
		}
		
		Order order = currentOrders.get(tableID);
		order.viewOrder();
	}
	
	private static void printInvoice() {
		System.out.print("Enter TableID: ");
		int tableID = Integer.parseInt(s.nextLine());
		
		// Sanity checks
		if (!tableList.exists(tableID)) {
			System.out.println("No table with this tableID currently exists!");
			return;
		}
		
		if (tableList.getTableList().get(tableID).getStatus() != TableStatus.OCCUPIED) {
			System.out.println("No orders currently being served at this table!");
			return;
		}
		
		Order order = currentOrders.get(tableID);
		
		// Print invoice
		order.printOrderInvoice();
		
		// Add the order to the report object
		report.addOrder(order);
		
		// Remove from currently stored orders
		currentOrders.remove(tableID);
		
		// Change the TableStatus to VACANT
		Table table = tableList.getTableList().get(tableID);
        	table.setStatus(TableStatus.VACANT);
	}
				   
	// Print revenue report
	private static void printRevenueReport() {
		DateTimeFormatter reportDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		System.out.println("Choose period to view revenue report for");
		System.out.print("Enter start date (YYYY-MM-DD): ");
		String startDateStr = s.nextLine() + " 00:00";
		System.out.print("Enter end date (YYYY-MM-DD): ");
		String endDateStr = s.nextLine() + " 00:00";
		LocalDateTime startDate = LocalDateTime.parse(startDateStr, reportDateFormatter);
		LocalDateTime endDate = LocalDateTime.parse(endDateStr, reportDateFormatter);
		
		report.printReport(startDate, endDate);
	}
			   
	// File I/O utility functions
	private static void initializeData() {
		String tableFile = "tables";
		String menuFile = "menu";
		String staffFile = "staff";
		String memberFile = "members";

		try {
			/* FOR TABLES */
			FileInputStream fiStream = new FileInputStream(tableFile);
			BufferedInputStream biStream = new BufferedInputStream(fiStream);
			ObjectInputStream diStream = new ObjectInputStream(biStream);
			@SuppressWarnings("unchecked") // The object saved is the tableList of the form Hashtable<Integer, Table>
			Hashtable<Integer, Table> tl = (Hashtable<Integer, Table>)diStream.readObject();
			tableList.setTableList(tl);
			Table.setCount(diStream.readInt());
			diStream.close();

			/* FOR MENU */
			FileInputStream fiStreamMenu = new FileInputStream(menuFile);
			BufferedInputStream biStreamMenu = new BufferedInputStream(fiStreamMenu);
			ObjectInputStream diStreamMenu = new ObjectInputStream(biStreamMenu);
			// menu = (Menu)diStreamMenu.readObject();
			@SuppressWarnings("unchecked")
			Hashtable<Integer, OrderableItems> m = (Hashtable<Integer, OrderableItems>)diStreamMenu.readObject();
			menu.setOrderableItems(m);
			MenuItem.setCount(diStreamMenu.readInt());
			PromotionalSetPackage.setCounter(diStreamMenu.readInt());
			diStreamMenu.close();

			/* FOR STAFF */
			FileInputStream fiStreamStaff = new FileInputStream(staffFile);
			BufferedInputStream biStreamStaff = new BufferedInputStream(fiStreamStaff);
			ObjectInputStream diStreamStaff = new ObjectInputStream(biStreamStaff);
			// staffList = (StaffList)diStreamStaff.readObject();
			@SuppressWarnings("unchecked")
			ArrayList<Staff> s = (ArrayList<Staff>)diStreamStaff.readObject();
			staffList.setStaffList(s);
			Staff.setCount(diStreamStaff.readInt());
			diStreamStaff.close();
			
			/* FOR MEMBERS */
			FileInputStream fiStreamMember = new FileInputStream(memberFile);
			BufferedInputStream biStreamMember = new BufferedInputStream(fiStreamMember);
			ObjectInputStream diStreamMember = new ObjectInputStream(biStreamMember);
			// staffList = (StaffList)diStreamStaff.readObject();
			@SuppressWarnings("unchecked")
			Hashtable<Integer, Member> me = (Hashtable<Integer, Member>)diStreamMember.readObject();
			memberList.setMembers(me);
			Member.setCount(diStreamMember.readInt());
			diStreamMember.close();
			
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
				foStream = new FileOutputStream(memberFile);
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
	
	private static void saveData() {
		String tableFile = "tables";
		String menuFile = "menu";
		String staffFile = "staff";
		String memberFile = "members";

		try {
			/* FOR TABLES */
			FileOutputStream foStream = new FileOutputStream(tableFile);
			BufferedOutputStream boStream = new BufferedOutputStream(foStream);
			ObjectOutputStream doStream = new ObjectOutputStream(boStream);
			doStream.writeObject(tableList.getTableList());
			doStream.writeInt(Table.getCount());
			doStream.close();
			
			/* FOR MENU */
			FileOutputStream foStreamMenu = new FileOutputStream(menuFile);
			BufferedOutputStream boStreamMenu = new BufferedOutputStream(foStreamMenu);
			ObjectOutputStream doStreamMenu = new ObjectOutputStream(boStreamMenu);
			doStreamMenu.writeObject(menu.getOrderableItems());
			doStreamMenu.writeInt(MenuItem.getCount());
			doStreamMenu.writeInt(PromotionalSetPackage.getCounter());
			doStreamMenu.close();

			/* FOR STAFF */
			FileOutputStream foStreamStaff = new FileOutputStream(staffFile);
			BufferedOutputStream boStreamStaff = new BufferedOutputStream(foStreamStaff);
			ObjectOutputStream doStreamStaff = new ObjectOutputStream(boStreamStaff);
			doStreamStaff.writeObject(staffList.getStaffList());
			doStreamStaff.writeInt(Staff.getCount());
			doStreamStaff.close();
			
			/* FOR MEMBER */
			FileOutputStream foStreamMember = new FileOutputStream(memberFile);
			BufferedOutputStream boStreamMember = new BufferedOutputStream(foStreamMember);
			ObjectOutputStream doStreamMember = new ObjectOutputStream(boStreamMember);
			doStreamMember.writeObject(memberList.getMembers());
			doStreamMember.writeInt(Member.getCount());
			doStreamMember.close();
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
	
	// For testing purposes (with simulation of different time)
	private static void testSubMenu() {
		int n;
		
		do {
			System.out.println("SIMULATE TIME");
			System.out.println("(1) Check table availability 23 hours way from now");
			System.out.println("(2) Check reservation 1 day and 15 minutes from now");
			System.out.println("(3) Check table availability 1 day and 15 minutes from now");
			System.out.println("(4) To Main Menu");
			System.out.print("Enter a choice: ");
			n = Integer.parseInt(s.nextLine());
			switch(n) {
				case 1:
					tableList.checkTableAvailabilityTest1();
					break;
				case 2:
					tableList.updateAllRezsTest();
					tableList.printAllRezs();
					break;
				case 3:
					tableList.checkTableAvailabilityTest2();
					break;
				case 4:
					break;
				default:
					System.out.println("Invalid input!");
					break;
			}
		} while (n != 4);
	}
}
