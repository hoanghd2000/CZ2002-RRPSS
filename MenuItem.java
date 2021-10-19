public class MenuItem extends OrderableItems{
    private String type;
    private double price;
    private String description;
    private String name;
    
    public MenuItem(String type, double price, String description, String name) {
        this.type = type;
        this.price = price;
        this.description = description;
        this.name = name;
    }

    // Default settings to the MenuItem initally
    public MenuItem(){
        type = "NOT SET";
        price = -1;
        description = "NOT SET";
        name = "NAMELESS";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object o){
        return ((MenuItem) o).getName() == this.name;
    }
}
