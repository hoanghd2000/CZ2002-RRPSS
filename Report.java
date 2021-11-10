import java.util.ArrayList;
import java.util.HashTable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Report {

    private ArrayList<Order> orders;

    public Report(){
        orders = new ArrayList<Order>();
    }

    public void addOrder(Order order){
        orders.add(order);
    }

    public void printReport(LocalDateTime startDateTime, LocalDateTime endDateTime){
        HashTable<OrderableItems, int> itemSet = new HashTable<OrderableItems, int>();
        ArrayList<int> itemIDs = new ArrayList<int>;
        double totalRevenue = 0;
        int qty;
        
        // Collate all items ordered within specified period
        for(Order order : orders){
            if(isWithinRange(order.getDateTime(), startDateTime, endDateTime)){
                //order.printOrderInvoice();
                Set<OrderableItems> orderItems = order.getItemSet().keySet();
                Set<OrderableItems> reportItems = itemSet.keySet();
                
                // For each item in current order, check if it is already included in report's set
                for (OrderableItems i : orderItems) {
                    qty = order.getItemQty(i.getItemID);
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

        // Print consolidated sales report
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd/MM/yyyy");
        
        System.out.println("===== Revenue Report =====");
        System.out.printf(startDateTime.format(formatter) + " - " + endDateTime.format(formatter));
        System.out.printf("%-4s %-20s\n", "Qty", "Item");
        System.out.printf("%-4s %-20s\n", "---", "----");

        // Print a la carte items
        for (OrderableItems i : reportItems) {
            if (i.getItemID() < 200) {
                System.out.printf("%-4d %-20s\n", set.get(i), i.getName());
            }
        }
        // Print promo set items
        for (OrderableItems i : reportItems) {
            if (i.getItemID() >= 200) {
                System.out.printf("%-4d %-20s\n", set.get(i), i.getName());
            }
        }
        System.out.println("-------------------------------");
        System.out.printf("Total revenue: %.2f\n", totalRevenue);
        
    }
    
    boolean isWithinRange(LocalDateTime dt, LocalDateTime start, LocalDateTime end) {
        return (dt.isBefore(start) || orderDateTime.isAfter(end));
    }
}
