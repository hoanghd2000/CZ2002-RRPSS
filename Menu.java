import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;

public class Menu implements Serializable{
    
    private Hashtable<Integer, OrderableItems> orderableItems;

    public Menu() {
        orderableItems = new Hashtable<Integer, OrderableItems>();
    }

    
    /** 
     * @param item
     */
    public void addMenuItem(MenuItem item) {
        orderableItems.put(item.getItemID(), item);
    }

    
    /** 
     * @param setPackage
     */
    public void addPromotionalSetPackage(PromotionalSetPackage setPackage) {
        orderableItems.put(setPackage.getItemID(), setPackage);
    }

    
    /** 
     * @param itemID
     */
    // generate javadoc for this method
    public void removeItem(int itemID){
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        OrderableItems item = orderableItems.remove(itemID);
        if(item == null){
            System.out.println("Item not found");
            return;
        } else {
            Collection<OrderableItems> items = orderableItems.values();
            for(OrderableItems i : items){
                if(i.getItemID() > 199 && ((PromotionalSetPackage) i).containsMenuItem((MenuItem) item)){
                    indexes.add(i.getItemID());
                }
            }
            for(int i : indexes){
                orderableItems.remove(i);
            }
        }
        System.out.println("Item and all promo packages containing the item removed successfully.");
    }
    
    
    /** 
     * @param itemID
     * @return Double
     */
    public Double getItemPrice(int itemID) {
        OrderableItems item = orderableItems.get(itemID);
        if(item == null){
            System.out.println("Item not found");
            return null;
        }
        return item.getPrice();
    }

    
    /** 
     * @param itemID
     * @return OrderableItems
     */
    public OrderableItems getItem(int itemID) {
        return orderableItems.get(itemID);
    }

    public void printMenu(){
        System.out.println();
        System.out.println("\t\tMenu Items");
        System.out.println("");
        System.out.println("itemID\t\tItem Name" + "\t\tPrice(S$)");
        System.out.println("================================================");
        System.out.println("\t\tAla Carte");
        System.out.println("");
        Set<Integer> set = orderableItems.keySet();
        for(Integer i : set){
            if(orderableItems.get(i).getItemID() < 200)
            System.out.println(orderableItems.get(i).getItemID() + "\t\t" + orderableItems.get(i).getName() + "\t\t" + orderableItems.get(i).getPrice());
        }
        System.out.println("================================================");
        System.out.println("\tPromotional Set Packages");
        for(Integer i : set){
            if(orderableItems.get(i).getItemID() >= 200)
            System.out.println(orderableItems.get(i).getItemID() + "\t\t" + orderableItems.get(i).getName() + "\t\t" + orderableItems.get(i).getPrice());
        }
        System.out.println("");
        System.out.println("===================END OF MENU==================");
        System.out.println("");
    }
    
}
