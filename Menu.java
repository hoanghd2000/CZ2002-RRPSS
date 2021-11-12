import java.util.Hashtable;
import java.util.Set;

public class Menu {
    
    //private ArrayList<OrderableItems> orderableItems;

    private Hashtable<Integer, OrderableItems> orderableItems;

    public Menu() {
        orderableItems = new Hashtable<Integer, OrderableItems>();
    }

    public void addMenuItem(MenuItem item) {
        orderableItems.put(item.getItemID(), item);
    }

    public void addPromotionalSetPackage(PromotionalSetPackage setPackage) {
        orderableItems.put(setPackage.getItemID(), setPackage);
    }

    // generate javadoc for this method
    public void removeItem(int itemID){
        OrderableItems item = orderableItems.remove(itemID);
        if(item == null){
            System.out.println("Item not found");
        }
        System.out.println("Item removed");
    }
    
    public Double getItemPrice(int itemID) {
        OrderableItems item = orderableItems.get(itemID);
        if(item == null){
            System.out.println("Item not found");
            return null;
        }
        return item.getPrice();
    }

    public OrderableItems getItem(int itemID) {
        return orderableItems.get(itemID);
    }

    public void printMenu(){
        System.out.println("Menu Items");
        System.out.println("Ala Carte");
        Set<Integer> set = orderableItems.keySet();
        for(Integer i : set){
            if(orderableItems.get(i).getItemID() < 200)
            System.out.println(orderableItems.get(i).getItemID() + " " + orderableItems.get(i).getName() + " " + orderableItems.get(i).getPrice());
        }
        System.out.println("================");
        System.out.println("Promotional Set Packages");
        for(Integer i : set){
            if(orderableItems.get(i).getItemID() >= 200)
            System.out.println(orderableItems.get(i).getItemID() + " " + orderableItems.get(i).getName() + " " + orderableItems.get(i).getPrice());
        }
    }
}
