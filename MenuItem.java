/**
 * This class denotes a menu item.
 * @author SS10 G5
 * @version 1.0
 * @since 2021-10-25
 */

public class MenuItem extends OrderableItems{

    // this variable will help with auto incrementing itemID when adding new items
    private static int count = 1;

    private String name;
    private String description;
    private double price;
    private int itemID;
    private String type;

    // creating an empty constructor
    public MenuItem() {
        super();
        itemID = count;
        count++;
    }

    // creating a constructor with parameters
    public MenuItem(String name, String description, double price, String type) {
        super();
        this.name = name;
        this.description = description;
        this.price = price;
        this.itemID = count;
        count++;
        this.type = type;
    }

    
    /** 
     * @return String
     */
    // creating getters and setters
    public String getName() {
        return name;
    }

    
    /** 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /** 
     * @return String
     */
    public String getDescription() {
        return description;
    }

    
    /** 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }   

    
    /** 
     * @return double
     */
    public double getPrice() {
        return price;
    }

    
    /** 
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    
    /** 
     * @return int
     */
    public int getItemID() {
        return itemID;
    }

    
    /** 
     * @return String
     */
    public String getType() {
        return type;
    }

    
    /** 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }
}