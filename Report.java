import java.util.ArrayList;
import java.time.LocalDateTime;

public class Report {

    private ArrayList<Order> orders;

    public Report(){
        orders = new ArrayList<Order>();
    }

    public void addOrder(Order order){
        orders.add(order);
    }

    public void printReport(LocalDateTime startDateTime, LocalDateTime endDateTime){
        double totalRevenue = 0;

        for(Order order : orders){
            if(isWithinRange(order.getDateTime(), startDateTime, endDateTime)){
                System.out.println(order.printOrderInvoice());
                totalRevenue += order.getPrice();
            }
        }

        System.out.printf("Total revenue: %.2f\n", totalRevenue);
        
    }
    
    boolean isWithinRange(LocalDateTime dt, LocalDateTime start, LocalDateTime end) {
        return (dt.isBefore(start) || orderDateTime.isAfter(end));
    }
}
