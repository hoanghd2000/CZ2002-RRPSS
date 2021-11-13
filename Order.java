import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.Set;

public class Order {
    static final float MEM_DISCOUNT = (float) 0.1;  // 10% Discount on final price for members
    static final float SERV_CHARGE = (float) 0.1;   // 10% Service charge
    static final float GST = (float) 0.07;          // 7% GST
    private static int counter = 0;
    private int orderID;
    private Hashtable<OrderableItems, Integer> set;
    private double price;
    private int staffID;
    private String staffName;
    private LocalDateTime dateTime;
    private boolean isMember;
    private int tableID;

    public Order(int staffID, int tableID, boolean isMember, String staffName) {
        this.staffName = staffName;
        orderID = counter++;
        set = new Hashtable<OrderableItems, Integer>();
        dateTime = LocalDateTime.now();
        price = 0;
        this.staffID = staffID;
        this.tableID = tableID;
        this.isMember = isMember;
    }

    public void addItem(OrderableItems item, int quantity) {
        // Check if item exists on menu
        if (item == null) {
            System.out.println("No such item on the menu. Please input a valid itemID!");
            return;
        }
        
        // Update total price of order
        price += ((double)quantity * item.getPrice());
        
        // Check for repeated item and update where necessary
        Set<OrderableItems> keys = set.keySet();
        for (OrderableItems i : keys) {
            if (i.getItemID() == item.getItemID()) {
                set.replace(i, set.get(i) + quantity);
                return;
            }
        }
        
        // If new item, add to set
        set.put(item, quantity);
    }

    public boolean removeItem(OrderableItems item, int quantity) {        
        // Check for repeated item and update where necessary
        Set<OrderableItems> keys = set.keySet();
        for (OrderableItems i : keys) {
            if (i.getItemID() == item.getItemID()) {
                int val = set.get(i);
                
                // Fewer items than were requested to be removed
                if (val < quantity) {
                    System.out.println("Fewer than " + quantity + " such items currently in order. Removing all instances.");
                    price -= ((double)val * i.getPrice());
                    set.remove(i);
                }
                // More items than were requested to be removed
                else if (val > quantity) {
                    price -= ((double)quantity * i.getPrice());
                    set.replace(i, set.get(i) - quantity);
                }
                // Quantity requested to be removed matches existing amount
                else {
                    price -= ((double)quantity * i.getPrice());
                    set.remove(i);
                }
                return true;
            }
        }

        // Item not found in order
        System.out.println("Specified item not currently in order.");
        return false;
    }
    
    public Hashtable<OrderableItems, Integer> getItemSet() { return set; }
    
    public int getItemQty(int itemID) {
        for (OrderableItems i : set.keySet()) {
            if (i.getItemID() == itemID) {
                return set.get(i);
            }
        }
        return 0;
    }

    public LocalDateTime getDateTime() { return dateTime; }

    public double getSubtotal() { return price; }
    
    public double getServCharge() { return price * SERV_CHARGE; }

    public double getGST() { return (price + getServCharge()) * GST; }

    public double getDiscount() { return (price + getServCharge() + getGST()) * MEM_DISCOUNT; }

    public double getFinalPrice() {        
        double p = price + getServCharge() + getGST();
        if (isMember) {
            // Orders made by a member will receive a fixed discount on final price
            p -= getDiscount();
        }

        return Math.round(p * 100) / 100.0;
    }

    public int getStaffID() { return staffID; }

    public void setStaffID(int staffID) { this.staffID = staffID; }

    public void viewOrder() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd/MM/yyyy h:mm a");
        System.out.println("=== View Order (Payment Pending) ===");
        System.out.println("Order No: " + orderID + "\t" + dateTime.format(formatter));
        System.out.println("Server: " + staffName + "\t\tTable No: " + tableID);
        System.out.println("-------------------------------");

        // Print details of all items ordered
        Set<OrderableItems> keys = set.keySet();
        System.out.printf("%-4s %-20s %6s\n", "Qty", "Item", "Price");
        System.out.printf("%-4s %-20s %6s\n", "---", "----", "-----");

        // Print a la carte items
        for (OrderableItems i : keys) {
            if (i.getItemID() < 200) {
                System.out.printf("%-4d %-20s %6.2f\n", set.get(i), i.getName(), i.getPrice());
            }
        }
        // Print promo set items
        for (OrderableItems i : keys) {
            if (i.getItemID() >= 200) {
                System.out.printf("%-4d %-20s %6.2f\n", set.get(i), i.getName(), i.getPrice());
            }
        }
        System.out.println("-------------------------------");

        // Print subtotal only
        System.out.printf("%22s %9.2f\n", "Subtotal", getSubtotal());
        System.out.println("-------------------------------");

    }
    
    public void printOrderInvoice() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd/MM/yyyy h:mm a");

        System.out.println("======== Order Invoice ========");
        System.out.println("Order No: " + orderID + "\t" + dateTime.format(formatter));
        System.out.println("Server: " + staffName + "\t\tTable No: " + tableID);
        System.out.println("-------------------------------");

        // Print details of all items ordered
        Set<OrderableItems> keys = set.keySet();
        System.out.printf("%-4s %-20s %6s\n", "Qty", "Item", "Price(S$");
        System.out.printf("%-4s %-20s %6s\n", "---", "----", "-----");

        // Print a la carte items
        for (OrderableItems i : keys) {
            if (i.getItemID() < 200) {
                System.out.printf("%-4d %-20s %6.2f\n", set.get(i), i.getName(), i.getPrice());
            }
        }
        // Print promo set items
        for (OrderableItems i : keys) {
            if (i.getItemID() >= 200) {
                System.out.printf("%-4d %-20s %6.2f\n", set.get(i), i.getName(), i.getPrice());
            }
        }
        System.out.println("-------------------------------");

        // Print prices
        System.out.printf("%22s %9.2f\n", "Subtotal", getSubtotal());
        System.out.printf("%22s %9.2f\n", "10% Service Charge", getServCharge());
        System.out.printf("%22s %9.2f\n", "7% GST", getGST());
        if (isMember) {
            System.out.printf("%22s  -%7.2f\n", "10% Mem Discount", getDiscount());
        }
        System.out.printf("%22s %9.2f\n", "Total", getFinalPrice());
        System.out.println("-------------------------------");
    }
}
