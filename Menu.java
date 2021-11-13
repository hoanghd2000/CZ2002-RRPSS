/**
 * This class represents a menu.
 * 
 * @author SS10 G5
 * @version 11.0
 * @since 2021-10-27
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;

public class Menu implements Serializable{
    
    private Hashtable<Integer, OrderableItems> orderableItems;

    
    /** 
     * @return Hashtable<Integer, OrderableItems>
     */
    public Hashtable<Integer, OrderableItems> getOrderableItems() {
		return orderableItems;
	}

    /** 
     * @param Hashtable<Integer, OrderableItems>
     */
	public void setOrderableItems(Hashtable<Integer, OrderableItems> orderableItems) {
		this.orderableItems = orderableItems;
	}


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
                if(item.getItemID()<200 && i.getItemID() > 199 && ((PromotionalSetPackage) i).containsMenuItem((MenuItem) item)){
                    indexes.add(i.getItemID());
                }
            }
            for(int i : indexes){
                orderableItems.remove(i);
            }
        }
        if(item.getItemID()<200) System.out.println("Item and all promo packages containing the item removed successfully.");
        else System.out.println("Promo package removed successfully.");
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
        System.out.println("\t\tAla Carte\n");
        Set<Integer> set = orderableItems.keySet();
        for(Integer i : set){
            if(orderableItems.get(i).getItemID() < 200)
            System.out.printf("%-4d %-20s %6.2f\n", orderableItems.get(i).getItemID(), orderableItems.get(i).getName(), orderableItems.get(i).getPrice());
        }
        System.out.println("================================================");
        System.out.println("\tPromotional Set Packages\n");
        for(Integer i : set){
            if(orderableItems.get(i).getItemID() >= 200)
            System.out.printf("%-4d %-20s %6.2f\n", orderableItems.get(i).getItemID(), orderableItems.get(i).getName(), orderableItems.get(i).getPrice());
        }
        System.out.println("");
        System.out.println("===================END OF MENU==================");
        System.out.println("");
    }
}
