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

    //creating getters and setters
    public double getPrice(){
        return this.price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public int getItemID(){
        return this.itemID;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

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
        /*
        if(this.set.containsKey(itemID)){
            this.set.replace(itemID, this.set.get(itemID) + quantity);
        }
        else{
            this.set.put(itemID, quantity);
        }
        */
    }

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
        /*
        if(this.set.containsKey(itemID) && this.set.get(itemID) >= quantity){
            this.set.replace(itemID, this.set.get(itemID) - quantity);
        }*/
    }
}