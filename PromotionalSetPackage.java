/**
 * This class represents a set of items that can be purchased together.
 * 
 * @author SS10 G5
 * @version 1.0
 * @since 2021-10-25
 */

import java.util.Hashtable;
import java.util.Set;

public class PromotionalSetPackage extends OrderableItems{

    // this counter makes itemID autoincrement 
    private static int counter = 200;

    // This hashtable has the itemID mapped to the quantity of that itemID
    Hashtable<MenuItem, Integer> set = new Hashtable<MenuItem, Integer>();

    private double price;

    private int itemID;

    private String description;

    private String name;

    //creating an empty constructor
    public PromotionalSetPackage(){
        super();
        this.price = 0;
        this.itemID = counter++;
    }

    //creating a constructor with a parameter
    public PromotionalSetPackage(double price, String description, String name){
        super();
        this.price = price;
        this.itemID = counter++;
        this.description = description;
        this.name = name;
    }

    
    /** 
     * @return double
     */
    //creating getters and setters
    public double getPrice(){
        return this.price;
    }

    
    /** 
     * @param price
     */
    public void setPrice(double price){
        this.price = price;
    }

    
    /** 
     * @return int
     */
    public int getItemID(){
        return this.itemID;
    }

    
    /** 
     * @return String
     */
    public String getName(){
        return this.name;
    }

    
    /** 
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    
    /** 
     * @return String
     */
    public String getDescription(){
        return this.description;
    }

    
    /** 
     * @param description
     */
    public void setDescription(String description){
        this.description = description;
    }

    
    /** 
     * @param item
     * @param quantity
     */
    public void addItem(MenuItem item, int quantity) {
        boolean found = false;
        Set<MenuItem> keys = set.keySet();
        for (MenuItem key : keys) {
            if (key.getItemID() == item.getItemID()) {
                int currentQuantity = set.get(key);
                set.put(key, currentQuantity + quantity);
                found = true;
                System.out.println("Item already existed in the promo. Quantity has been increased!");
            }
        }
        if (!found) {
            set.put(item, quantity);
            System.out.println("Item added to promo!");
        }
    }

    
    /** 
     * @param item
     * @param quantity
     */
    // remove item from the set
    public void removeItem(MenuItem item, int quantity) {
        Set<MenuItem> keys = set.keySet();
        boolean found = false;
        for (MenuItem key : keys) {
            if (key.getItemID() == item.getItemID()) {
                int currentQuantity = set.get(key);
                if (currentQuantity > quantity) {
                    set.put(key, currentQuantity - quantity);
                    System.out.println("Updated quantity for this item: " + (currentQuantity - quantity));
                }
                else if (currentQuantity == quantity) {
                    set.remove(key);
                    System.out.println("Item completely removed from promo!");
                }
                else {
                    System.out.println("Not enough quantity to remove!");
                }
                found = true;
            }
        }
        if (!found) {
            System.out.println("Item not found in promo!");
        }
    }

    
    /** 
     * @param item
     * @return boolean
     */
    public boolean containsMenuItem(MenuItem item) {
        Set<MenuItem> keys = set.keySet();
        for (MenuItem key : keys) {
            if (key.getItemID() == item.getItemID()) {
                return true;
            }
        }
        return false;
    }
}