import java.util.ArrayList;

import java.util.List;
import java.util.Date;

public class Report {

    private ArrayList<Order> orders;

    public Report(){
        orders = new ArrayList<Order>();
    }

    public void addOrder(Order order){
        orders.add(order);
    }

    public void printReport(Date startDate, Date endDate){
        double totalRevenue = 0;

        for(Order order : orders){
            if(order.getDate().after(startDate) && order.getDate().before(endDate)){
                System.out.println(order.printOrderInvoice());
                totalRevenue += order.getTotalPrice();
            }
        }

        System.out.println("Total revenue: " + totalRevenue);
        
    }
    
}
