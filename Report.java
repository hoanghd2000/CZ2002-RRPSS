/**
 * This class contains the arrayList of orders to be accessed
 * to print the total Revenue earned.
 * 
 * @author SS10 G5
 * @version 1.0
 * @since 2021-10-25
 */

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Report {

    private ArrayList<Order> orders;

    public Report(){
        orders = new ArrayList<Order>();
    }

    
    /** 
     * @param order
     */
    public void addOrder(Order order){
        orders.add(order);
    }

    
    /** 
     * @param startDateTime
     * @param endDateTime
     */
    public void printReport(LocalDateTime startDateTime, LocalDateTime endDateTime){
        Hashtable<OrderableItems, Integer> itemSet = new Hashtable<OrderableItems, Integer>();
        ArrayList<Integer> itemIDs = new ArrayList<Integer>();
        double totalRevenue = 0;
        int qty;

        Set<OrderableItems> orderItems = null;
        Set<OrderableItems> reportItems = null;
        
        // Collate all items ordered within specified period
        for(Order order : orders){
            if(isWithinRange(order.getDateTime(), startDateTime, endDateTime)){
                //order.printOrderInvoice();
                orderItems = order.getItemSet().keySet();
                reportItems = itemSet.keySet();
                
                // For each item in current order, check if it is already included in report's set
                for (OrderableItems i : orderItems) {
                    qty = order.getItemQty(i.getItemID());
                    if (itemIDs.contains(i.getItemID())) {
                        // Add specified quantity of item to itemSet
                        itemSet.replace(i, itemSet.get(i) + qty);
                    }
                    else {
                        // Add new item to itemSet
                        itemSet.put(i, qty);
                        itemIDs.add(i.getItemID());
                    }
                }
                                
                totalRevenue += order.getFinalPrice();
            }
        }

        // If nothing to print, display error message
        if (reportItems == null) {
            System.out.println("No orders found during this period!");
            return;
        }

        // Print consolidated sales report
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd/MM/yyyy");
        
        System.out.println("===== Revenue Report =====");
        System.out.println(startDateTime.format(formatter) + " - " + endDateTime.format(formatter));
        System.out.printf("%-4s %-20s\n", "Qty", "Item");
        System.out.printf("%-4s %-20s\n", "---", "----");

        // Print a la carte items
        for (OrderableItems i : reportItems) {
            if (i.getItemID() < 200) {
                System.out.printf("%-4d %-20s\n", itemSet.get(i), i.getName());
            }
        }
        // Print promo set items
        for (OrderableItems i : reportItems) {
            if (i.getItemID() >= 200) {
                System.out.printf("%-4d %-20s\n", itemSet.get(i), i.getName());
            }
        }
        System.out.println("-------------------------------");
        System.out.printf("Total revenue: S$ %.2f\n", totalRevenue);
        System.out.println("-------------------------------");
    }
    
    
    /** 
     * @param dt
     * @param start
     * @param end
     * @return boolean
     */
    boolean isWithinRange(LocalDateTime dt, LocalDateTime start, LocalDateTime end) {
        return (!(dt.isBefore(start) || dt.isAfter(end)));
    }
}
