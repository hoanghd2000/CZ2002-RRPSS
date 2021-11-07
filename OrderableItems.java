abstract class OrderableItems {

    // returns the price of the item
    public abstract double getPrice();
    
    // helps set the price of the item
    public abstract void setPrice(double price);

    // helps get the itemID of the item
    public abstract int getItemID();

    public abstract String getName();
}
