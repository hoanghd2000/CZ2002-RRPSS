import java.util.Hashtable;

public class PromotionalSetPackage extends OrderableItems{

    // this counter makes itemID autoincrement 
    private static int counter = 200;

    // This hashtable has the itemID mapped to the quantity of that itemID
    Hashtable<Integer, Integer> set = new Hashtable<Integer, Integer>();

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

    public void addItem(int itemID, int quantity) {
        if(this.set.containsKey(itemID)){
            this.set.replace(itemID, this.set.get(itemID) + quantity);
        }
        else{
            this.set.put(itemID, quantity);
        }
    }

    // remove item from the set
    public void removeItem(int itemID, int quantity) {
        if(this.set.containsKey(itemID) && this.set.get(itemID) >= quantity){
            this.set.replace(itemID, this.set.get(itemID) - quantity);
        }
    }
}