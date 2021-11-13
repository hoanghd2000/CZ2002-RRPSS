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

    // creating getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }   

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getItemID() {
        return itemID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}