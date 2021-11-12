import java.io.Serializable;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;

public class Menu implements Serializable{
    
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
            return;
        } else {
            Collection<OrderableItems> items = orderableItems.values();
            for(OrderableItems i : items){
                if(i.getItemID() > 199 && ((PromotionalSetPackage) i).containsMenuItem((MenuItem) item)){
                    orderableItems.remove(i.getItemID());
                }
            }
        }
        System.out.println("Item and all promo packages containing the item removed successfully.");
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
