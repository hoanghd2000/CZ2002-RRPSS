public class MenuItem{

    private double price;

    private String name;

    private String description;

    private boolean isPartOfPromo;

    /**
     * this is an object indicating the type of menu item.
     * this is a separate class as it needs to be indcluded for the 
     * promo-package class. 
     */
    private MenuItemType type;

    public MenuItem(){
        price = 0;
        name = "NOT SET";
        description = "No description for this item available at the time.";
        isPartOfPromo = false;
        type.setType("NOT SET");
    }

    /**
     * @param price
     * @param description
     * @param isPartOfPromo
     * @param type
     * @param name
     */
    public MenuItem(double price, String description, boolean isPartOfPromo, String type, String name){
        this.name = name;
        this.price = price;
        this.description = description;
        this.isPartOfPromo = isPartOfPromo;
        this.type.setType(type);
    }

    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type.getType();
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type.setType(type);
    }

    public double getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPartOfPromo() {
        return isPartOfPromo;
    }

    /**
     * @param isPartOfPromo
     */
    public void setPartOfPromo(boolean isPartOfPromo) {
        this.isPartOfPromo = isPartOfPromo;
    }
}
